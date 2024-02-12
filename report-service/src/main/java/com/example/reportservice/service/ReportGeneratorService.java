package com.example.reportservice.service;


import com.example.reportservice.core.entity.AuditEntity;
import com.example.reportservice.service.api.IReportGenerator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Qualifier("excel-file-generator")
public class ReportGeneratorService implements IReportGenerator {

    private final String[] headers = {
            "uuid",
            "dt_create",
            "user_id",
            "user_email",
            "user_fio",
            "user_role",
            "action",
            "essence_type",
            "essence_type_id"
    };

    @Override
    public void generate(List<AuditEntity> audits, UUID name) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Report Data");
        createHeader(spreadsheet);

        int rowId = 1;
        for (AuditEntity audit : audits) {
            Object[] objectArray = convertToArray(audit);
            createRow(spreadsheet, rowId, objectArray);
            rowId++;
        }
        FileOutputStream out = new FileOutputStream(name + ".xlsx");
        workbook.write(out);
        out.close();
    }

    private void createHeader(XSSFSheet spreadsheet) {
        createRow(spreadsheet, 0, headers);
    }

    private void createRow(XSSFSheet spreadsheet, int rowId, Object[] objectArray) {
        XSSFRow row = spreadsheet.createRow(rowId);
        int cellId = 0;
        for (Object object : objectArray) {
            Cell cell = row.createCell(cellId);
            cell.setCellValue((String) object);
            cellId++;
        }
    }

    private Object[] convertToArray(AuditEntity audit) {
        return new Object[] {
                audit.getUuid().toString(),
                audit.getDtCreate().toString(),
                audit.getId().toString(),
                audit.getMail(),
                audit.getFio(),
                audit.getRole().toString(),
                audit.getText().toString(),
                audit.getEssenceType().toString(),
                audit.getEssenceId()
        };
    }
}
