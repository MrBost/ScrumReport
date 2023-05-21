package com.activedge.scrum.report.util;

import com.activedge.scrum.report.data.OutputData;
import com.activedge.scrum.report.data.OutputDataDTO;
import com.activedge.scrum.report.data.ReportData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

public class ScrumOutputExportUtil extends ExportUtil<OutputData> {
    int rownum = 1;
    @Override
    void fillData(List<OutputData> dataList) {
        CellStyle normalStyle = getNormalStyle();
        CellStyle wrapStyle = getWrapStyle();
        CellStyle cs = getAmountFormat(wb);

        for (OutputData rev : dataList) {
            Row row = sh.createRow(rownum);
            Cell cell_0 = row.createCell(0, CellType.STRING);
            cell_0.setCellStyle(normalStyle);
            cell_0.setCellValue(rev.getSNo());

            Cell cell_1 = row.createCell(1, CellType.STRING);
            cell_1.setCellStyle(normalStyle);
            cell_1.setCellValue(rev.getSquad());

            Cell cell_2 = row.createCell(2, CellType.STRING);
            cell_2.setCellStyle(normalStyle);
            cell_2.setCellValue(rev.getAssignedTo());

            Cell cell_2a = row.createCell(3, CellType.STRING);
            cell_2a.setCellStyle(wrapStyle);
            cell_2a.setCellValue(rev.getSquadTeam());

            Cell cell_3 = row.createCell(4, CellType.STRING);
            cell_3.setCellStyle(normalStyle);
            cell_3.setCellValue(rev.getIterationPath());

            Cell cell_4 = row.createCell(5, CellType.STRING);
            cell_4.setCellStyle(normalStyle);
            cell_4.setCellValue(rev.getId());

            Cell cell_5 = row.createCell(6, CellType.STRING);
            cell_5.setCellStyle(normalStyle);
            cell_5.setCellValue("PBI");

            Cell cell_6 = row.createCell(7, CellType.STRING);
            cell_6.setCellStyle(normalStyle);
            cell_6.setCellValue(rev.getTitle());

            Cell cell_7 = row.createCell(8, CellType.STRING);
            cell_7.setCellStyle(normalStyle);
            cell_7.setCellValue(rev.getCreatedDate());

            Cell cell_8 = row.createCell(9, CellType.STRING);
            cell_8.setCellStyle(normalStyle);
            cell_8.setCellValue(rev.getActivatedDate());

            Cell cell_9 = row.createCell(10, CellType.STRING);
            cell_9.setCellStyle(normalStyle);
            cell_9.setCellValue(rev.getExpectedEndDate());

            Cell cell_10 = row.createCell(11, CellType.STRING);
            cell_10.setCellStyle(cs);
            cell_10.setCellValue(rev.getDurationInSprintWks());

            Cell cell_11 = row.createCell(12, CellType.STRING);
            cell_11.setCellStyle(normalStyle);
            cell_11.setCellValue(rev.getState());

            Cell cell_12 = row.createCell(13, CellType.STRING);
            cell_12.setCellStyle(normalStyle);
            cell_12.setCellValue(rev.getDod());

            Cell cell_13 = row.createCell(14, CellType.STRING);
            cell_13.setCellStyle(normalStyle);
            cell_13.setCellValue(rev.getStateValue());

            Cell cell_14 = row.createCell(15, CellType.STRING);
            cell_14.setCellStyle(normalStyle);
            cell_14.setCellValue(rev.getStateDod());

            Cell cell_17 = row.createCell(16, CellType.STRING);
            cell_17.setCellStyle(normalStyle);
            cell_17.setCellValue(rev.getRatio());

            Cell cell_18 = row.createCell(17, CellType.STRING);
            cell_18.setCellStyle(normalStyle);
            cell_18.setCellValue(rev.getScheduleRatio());

            Cell cell_19 = row.createCell(18, CellType.STRING);
            cell_19.setCellStyle(normalStyle);
            cell_19.setCellValue(rev.getSpi());

            Cell cell_20 = row.createCell(19, CellType.STRING);
            cell_20.setCellStyle(normalStyle);
            cell_20.setCellValue(rev.getSpiPerSquad());

            Cell cell_21 = row.createCell(20, CellType.STRING);
            cell_21.setCellStyle(normalStyle);
            cell_21.setCellValue(rev.getDurationInSprint());

            Cell cell_22 = row.createCell(21, CellType.STRING);
            cell_22.setCellStyle(normalStyle);
            cell_22.setCellValue(rev.getPbiItemAgeInDays());

            rownum++;
        }

    }
    public static CellStyle getAmountFormat(XSSFWorkbook wb) {
        DataFormat df = wb.createDataFormat();
        CellStyle cs = wb.createCellStyle();
        cs.setDataFormat(df.getFormat("##,###,##0.00"));
        return cs;
    }
}
