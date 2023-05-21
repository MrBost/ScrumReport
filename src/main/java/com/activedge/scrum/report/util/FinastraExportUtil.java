package com.activedge.scrum.report.util;

import com.activedge.scrum.report.data.FinastraData;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

public class FinastraExportUtil extends FinastraUtil<FinastraData> {

    @Override
    void fillData(List<FinastraData> dataList) {
        CellStyle normalStyle = getNormalStyle();
        int rowNum = 1;

        for (FinastraData rev : dataList) {
            Row row = sh.createRow(rowNum);
            Cell cell_0 = row.createCell(0, CellType.STRING);
            cell_0.setCellStyle(normalStyle);
            cell_0.setCellValue(rev.getItemKey());

            Cell cell_1 = row.createCell(1, CellType.STRING);
            cell_1.setCellStyle(normalStyle);
            cell_1.setCellValue(rev.getApplicant());

            Cell cell_2 = row.createCell(2, CellType.STRING);
            cell_2.setCellStyle(normalStyle);
            cell_2.setCellValue(rev.getCorrespBank());

            Cell cell_2a = row.createCell(3, CellType.STRING);
            cell_2a.setCellStyle(normalStyle);
            cell_2a.setCellValue(rev.getExpirationDate());

            Cell cell_3 = row.createCell(4, CellType.STRING);
            cell_3.setCellStyle(normalStyle);
            cell_3.setCellValue(rev.getBeneficiary());

            rowNum++;
        }
    }

}
