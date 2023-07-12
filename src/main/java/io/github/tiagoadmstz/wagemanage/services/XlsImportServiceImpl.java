package io.github.tiagoadmstz.wagemanage.services;

import io.github.tiagoadmstz.wagemanage.entities.*;
import io.github.tiagoadmstz.wagemanage.repositories.ContactDetailsRepository;
import io.github.tiagoadmstz.wagemanage.repositories.PersonRepository;
import io.github.tiagoadmstz.wagemanage.repositories.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@Service
class XlsImportServiceImpl implements IXlsImportService {

    private final Logger logger = LogManager.getLogger(XlsImportServiceImpl.class);
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    public XlsImportServiceImpl(PersonRepository personRepository, RoleRepository roleRepository, ContactDetailsRepository contactDetailsRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public boolean importXls(final String fileName) {
        getXlsFile(fileName).ifPresent(workbook -> {
            List<Sheet> sheets = getSheets(workbook);
            List<Role> roleList = mountRoleList(sheets.get(1));
            Sheet personSheet = sheets.get(0);
            List<XlsRow> personRows = getRows(personSheet);
            List<ContactDetails> contactDetailsList = mountContactDetailsList(personRows);
            personRows.subList(1, personRows.size()).forEach(row -> {
                Person person = new Person();
                person.setId(Double.valueOf(row.columns.get(0).toString()).longValue());
                person.setName(row.columns.get(1).toString());
                person.setBirthdate(LocalDate.parse(row.columns.get(9).toString(), DateTimeFormatter.ofPattern("M/d/yyyy")));

                //person.setContactDetails(contactDetails);

                /*City city = new City();
                city.setName(row.columns.get(2).toString());*/

                /*Country country = new Country();
                country.setNome(row.columns.get(6).toString());
                city.setCountry(country);*/

                /*Undress undress = new Undress();
                undress.setCity(city);
                undress.setZipCode(row.columns.get(4).toString());
                undress.setStreetName(row.columns.get(5).toString());
                person.setUndress(undress);*/

                /*WageUser wageUser = new WageUser();
                wageUser.setUsername(row.columns.get(7).toString());
                person.setUser(wageUser);*/

                try {
                    long roleId = Double.valueOf(row.columns.get(10).toString()).longValue();
                    roleList.stream().filter(role -> role.getId().equals(roleId)).forEach(person::setRole);
                    personRepository.save(person);
                } catch (NullPointerException nullPointerException) {
                    logger.error("Person has no role");
                }
            });
        });
        personRepository.findAll().forEach(System.out::println);
        return true;
    }

    private List<ContactDetails> mountContactDetailsList(final List<XlsRow> personRows) {
        final List<ContactDetails> contactDetailsList = new ArrayList<>();
        personRows.subList(1, personRows.size()).stream()
                .map(row -> {
                    ContactDetails contactDetails = new ContactDetails();
                    contactDetails.setEmail(row.columns.get(3).toString());
                    contactDetails.setPhoneNumber(row.columns.get(8).toString());
                    contactDetailsRepository.save(contactDetails);
                    return contactDetails;
                }).forEach(contactDetailsList::add);
        return contactDetailsList;
    }

    private List<Role> mountRoleList(Sheet roles) {
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
            final File dataFile = new ClassPathResource(fileName).getFile();
            final FileInputStream input = new FileInputStream(dataFile);
            final Workbook workbook = loadWorkbook(input);
            input.close();
            if (dataFile.isFile() && dataFile.exists()) {
                return Optional.of(workbook);
            }
        } catch (IOException ioException) {
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
