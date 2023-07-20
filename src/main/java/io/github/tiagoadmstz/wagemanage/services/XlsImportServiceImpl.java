package io.github.tiagoadmstz.wagemanage.services;

import io.github.tiagoadmstz.wagemanage.entities.*;
import io.github.tiagoadmstz.wagemanage.repositories.*;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@Named
class XlsImportServiceImpl implements IXlsImportService {

    private final Logger logger = LogManager.getLogger(XlsImportServiceImpl.class);
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final ContactDetailsRepository contactDetailsRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final UndressRepository undressRepository;
    private final WageUserRepository wageUserRepository;

    @Inject
    private XlsImportServiceImpl(PersonRepository personRepository, RoleRepository roleRepository, ContactDetailsRepository contactDetailsRepository, CityRepository cityRepository, CountryRepository countryRepository, UndressRepository undressRepository, WageUserRepository wageUserRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.contactDetailsRepository = contactDetailsRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.undressRepository = undressRepository;
        this.wageUserRepository = wageUserRepository;
    }

    @Override
    public boolean importXls(final String fileName) {
        getXlsFile(fileName).ifPresent(workbook -> {
            final List<Sheet> sheets = getSheets(workbook);
            final List<Role> roleList = saveRoleList(sheets.get(1));
            final Sheet personSheet = sheets.get(0);
            final List<XlsRow> personRows = getRows(personSheet);
            personRows.subList(1, personRows.size()).forEach(row -> {
                final long personId = Double.valueOf(row.columns.get(0).toString()).longValue();
                final String personName = row.columns.get(1).toString();
                final String cityName = row.columns.get(2).toString();
                final String email = row.columns.get(3).toString();
                final String zipCode = row.columns.get(4).toString();
                final String streetName = row.columns.get(5).toString();
                final String countryName = row.columns.get(6).toString();
                final String username = row.columns.get(7).toString();
                final String phoneNumber = row.columns.get(8).toString();
                final String birthdate = row.columns.get(9).toString();
                final Object roleId = row.columns.get(10);
                savePerson(personId, personName, cityName, email, zipCode, streetName, countryName, username, phoneNumber, birthdate, roleId, roleList);
            });
        });
        personRepository.findAll().forEach(System.out::println);
        return true;
    }

    private void savePerson(
            final Long personId, final String personName, final String cityName,
            final String email, final String zipCode, final String streetName,
            final String countryName, final String username, final String phoneNumber,
            final String birthdate, final Object roleId, final List<Role> roleList
    ) {
        Person person = new Person();
        person.setId(personId);
        person.setName(personName);
        person.setBirthdate(LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("M/d/yyyy")));

        final ContactDetails contactDetails = contactDetailsRepository.findByEmailAndPhoneNumber(email, phoneNumber)
                .orElseGet(() -> contactDetailsRepository.save(new ContactDetails(email, phoneNumber)));
        person.setContactDetails(contactDetails);

        final Country country = countryRepository.findByName(countryName)
                .orElseGet(() -> countryRepository.save(new Country(countryName)));

        final City city = cityRepository.findByName(cityName)
                .orElseGet(() -> cityRepository.save(new City(cityName, country)));

        final Undress undress = undressRepository.findByStreetNameAndZipCode(streetName, zipCode)
                .orElseGet(() -> undressRepository.save(new Undress(streetName, zipCode, city)));
        person.setUndress(undress);

        final WageUser wageUser = wageUserRepository.findByUsername(username)
                .orElseGet(() -> wageUserRepository.save(new WageUser(username)));
        person.setUser(wageUser);

        try {
            long roleIdLong = Double.valueOf(roleId.toString()).longValue();
            roleList.stream().filter(role -> role.getId().equals(roleIdLong)).forEach(person::setRole);
        } catch (NullPointerException nullPointerException) {
            logger.error("Person has no role");
        }
        personRepository.save(person);
    }

    private List<Role> saveRoleList(Sheet roles) {
        List<Role> roleList = new ArrayList<>();
        List<XlsRow> roleRows = getRows(roles);
        roleRows.subList(1, roleRows.size()).forEach(row -> {
            Role role = new Role();
            role.setId(Double.valueOf(row.columns.get(0).toString()).longValue());
            role.setName(row.columns.get(1).toString());
            role.setSalary(new BigDecimal(row.columns.get(2).toString()));
            roleRepository.save(role);
            roleList.add(role);
        });
        return roleList;
    }

    private Optional<Workbook> getXlsFile(final String fileName) {
        try {
            //TODO: test getting classpath file
            final File dataFile = new File(getClass().getResource(fileName).toURI());
            final FileInputStream input = new FileInputStream(dataFile);
            final Workbook workbook = loadWorkbook(input);
            input.close();
            if (dataFile.isFile() && dataFile.exists()) {
                return Optional.ofNullable(workbook);
            }
        } catch (URISyntaxException | IOException ioException) {
            logger.error("Error on try load XLS file, caus: {}", ioException.getMessage());
        }
        return Optional.empty();
    }

    private Workbook loadWorkbook(FileInputStream input) {
        try {
            return WorkbookFactory.create(input);
        } catch (IOException e) {
            logger.error("Error on try load xlsx workbook, cause: {}", e.getMessage());
        }
        return null;
    }

    private List<Sheet> getSheets(final Workbook workbook) {
        final int numberOfSheets = workbook.getNumberOfSheets();
        final List<Sheet> sheets = new ArrayList<>();
        IntStream.range(0, numberOfSheets)
                .boxed()
                .forEach(page -> sheets.add(workbook.getSheetAt(page)));
        return sheets;
    }

    private List<XlsRow> getRows(Sheet sheet) {
        final Iterator<Row> rowIterator = sheet.iterator();
        final List<XlsRow> xlsRowArrayList = new ArrayList<>();
        while (rowIterator.hasNext()) {
            final HSSFRow row = (HSSFRow) rowIterator.next();
            final Iterator<Cell> cellIterator = row.cellIterator();
            final XlsRow xlsRow = new XlsRow();
            while (cellIterator.hasNext()) {
                final Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case STRING:
                        xlsRow.putColumn(cell.getColumnIndex(), cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        xlsRow.putColumn(cell.getColumnIndex(), cell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        xlsRow.putColumn(cell.getColumnIndex(), cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        xlsRow.putColumn(cell.getColumnIndex(), cell.getCellFormula());
                        break;
                }
            }
            xlsRowArrayList.add(xlsRow);
        }
        return xlsRowArrayList;
    }

    private static class XlsRow {

        private final Map<Integer, Object> columns = new HashMap<>(1);

        public void putColumn(int index, Object value) {
            columns.put(index, value);
        }

        public Object getValue(int columnIndex) {
            return columns.get(columnIndex);
        }
    }
}
