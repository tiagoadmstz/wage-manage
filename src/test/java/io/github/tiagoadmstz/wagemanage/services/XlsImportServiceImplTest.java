package io.github.tiagoadmstz.wagemanage.services;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class XlsImportServiceImplTest {

    @Inject
    private IXlsImportService xlsImportService;

    @Test
    void importXlsTest() {
        Assertions.assertTrue(xlsImportService.importXls("Atividade_Tecnica_Java_DB.xls"));
    }
}