package io.github.tiagoadmstz.wagemanage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XlsImportServiceImplTest {

    @Autowired
    private IXlsImportService xlsImportService;

    @Test
    void importXlsTest() {
        Assertions.assertTrue(xlsImportService.importXls("Atividade_Tecnica_Java_DB.xls"));
    }
}