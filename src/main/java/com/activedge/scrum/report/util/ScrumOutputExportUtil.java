package com.activedge.scrum.report.util;

import com.activedge.scrum.report.data.OutputData;
import com.activedge.scrum.report.data.OutputDataDTO;
import com.activedge.scrum.report.data.ReportData;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.text.DecimalFormat;
import java.util.*;

import static com.activedge.scrum.report.util.State.*;

public class ScrumOutputExportUtil extends ExportUtil<OutputData> {

    public ScrumOutputExportUtil(){}
    public ScrumOutputExportUtil(int pv){
        this.pv = pv;
    }
    int rownum = 2;
    int red,green,blue;
    private int pv;
    @Override
    void fillData(List<OutputData> dataList) {
        CellStyle normalStyle = getNormalStyle();
        CellStyle pvStyle = getPvStyle();
        CellStyle stateStyle = getStateStyle();
        CellStyle altStateStyle = getStateAlternatingRowStyle();
        CellStyle stateDurationStyle;
        CellStyle alternateRow = getAlternatingRowStyle();
        CellStyle dateStyle = getDateFormat(wb);
        CellStyle caeDateStyle;
        CellStyle cellStyle;
        CellStyle ratio_1dp;
        CellStyle rstyle;
        CellStyle spiSquadStyle = getSpiSquadStyle();
        CellStyle ratioStyle = getRatio(wb);
        CellStyle altRatioStyle = getRatioAlternatingRowStyle(wb);
        CellStyle _1dpRatio = getRatio_1dp(wb);
        CellStyle alt_1dpRatio = getRatio_1dpAlternatingRowStyle(wb);
        CellStyle altDateStyle = getDateAlternatingRowStyle(wb);

        Row rowPV = sh.createRow(0);
        Cell cellPV = rowPV.createCell(17);
        cellPV.setCellStyle(pvStyle);
        cellPV.setCellValue("PV");

        Cell cellPVvalue = rowPV.createCell(18);
        cellPVvalue.setCellStyle(pvStyle);
        cellPVvalue.setCellValue(pv);

        for (OutputData data : dataList) {
            Row row = sh.createRow(rownum);
            int sn = data.getSNo();
            if(sn%2 != 0){
                cellStyle = alternateRow;
                ratio_1dp = alt_1dpRatio;
                rstyle = altRatioStyle;
                caeDateStyle = altDateStyle;
                stateDurationStyle = altStateStyle;
            }else {
                cellStyle = normalStyle;
                ratio_1dp = _1dpRatio;
                rstyle = ratioStyle;
                caeDateStyle = dateStyle;
                stateDurationStyle = stateStyle;
            }
            Cell cell_0 = row.createCell(0, CellType.STRING);
            cell_0.setCellStyle(cellStyle);
            cell_0.setCellValue(data.getSNo());

            Cell cell_1 = row.createCell(1, CellType.STRING);
            cell_1.setCellStyle(cellStyle);
            cell_1.setCellValue(data.getSquad());

            Cell cell_2 = row.createCell(2, CellType.STRING);
            cell_2.setCellStyle(cellStyle);
            cell_2.setCellValue(data.getAssignedTo());

            Cell cell_2a = row.createCell(3, CellType.STRING);
            cell_2a.setCellStyle(cellStyle);
            cell_2a.setCellValue(data.getSquadTeam());

            Cell cell_3 = row.createCell(4, CellType.STRING);
            cell_3.setCellStyle(cellStyle);
            cell_3.setCellValue(data.getIterationPath());

            Cell cell_4 = row.createCell(5, CellType.STRING);
            cell_4.setCellStyle(cellStyle);
            cell_4.setCellValue(data.getId());

            Cell cell_5 = row.createCell(6, CellType.STRING);
            cell_5.setCellStyle(cellStyle);
            cell_5.setCellValue("PBI");

            Cell cell_6 = row.createCell(7, CellType.STRING);
            cell_6.setCellStyle(cellStyle);
            cell_6.setCellValue(data.getTitle());

            Cell cell_7 = row.createCell(8, CellType.STRING);
            cell_7.setCellStyle(caeDateStyle);
            cell_7.setCellValue(data.getCreatedDate());

            Cell cell_8 = row.createCell(9, CellType.STRING);
            cell_8.setCellStyle(caeDateStyle);
            cell_8.setCellValue(data.getActivatedDate());

            Cell cell_9 = row.createCell(10, CellType.STRING);
            cell_9.setCellStyle(caeDateStyle);
            cell_9.setCellValue(data.getExpectedEndDate());

            Cell cell_10 = row.createCell(11, CellType.STRING);
            cell_10.setCellStyle(ratio_1dp);
            cell_10.setCellValue(data.getDurationInSprintWks());

            Cell cell_11 = row.createCell(12, CellType.STRING);
            String state = data.getState();
            if(StringUtils.isNotBlank(state) && state.equals(APPROVED.getValue())){
                red=249;green=191;blue=7;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(CHANGEMANAGEMENT.getValue())) {
                red=149;green=197;blue=117;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(DEVELOPMENT.getValue())) {
                red=241;green=171;blue=15;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(UAT.getValue())) {
                red=244;green=216;blue=178;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(CONTROLLEDLIVE.getValue())) {
                red=149;green=197;blue=117;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(SYSTEMTESTING.getValue())) {
                red=255;green=255;blue=1;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(DONE.getValue())) {
                red=96;green=148;blue=60;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(NEW.getValue())) {
                red=244;green=216;blue=178;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(ONHOLD.getValue())) {
                red=255;green=0;blue=0;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            }else if (StringUtils.isNotBlank(state) && state.equals(SITCOMPLETED.getValue())) {
                red=255;green=255;blue=1;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(SITONGOING.getValue())) {
                red=255;green=255;blue=1;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(UATCOMPLETED.getValue())) {
                red=244;green=216;blue=178;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(UATONGOING.getValue())) {
                red=244;green=216;blue=178;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            }else if (StringUtils.isNotBlank(state) && state.equals(DEVELOPMENTCOMPLETED.getValue())) {
                red=249;green=191;blue=7;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            } else if (StringUtils.isNotBlank(state) && state.equals(DEVELOPMENTONGOING.getValue())) {
                red=249;green=191;blue=7;
                cell_11.setCellStyle(getStateStyle(red,green,blue));
            }

            cell_11.setCellValue(state);

            Cell cell_12 = row.createCell(13, CellType.STRING);
            cell_12.setCellStyle(cellStyle);
            cell_12.setCellValue(data.getDod());

            Cell cell_13 = row.createCell(14, CellType.STRING);
            cell_13.setCellStyle(stateDurationStyle);
            cell_13.setCellValue(data.getStateValue());

            Cell cell_14 = row.createCell(15, CellType.STRING);
            cell_14.setCellStyle(stateDurationStyle);
            cell_14.setCellValue(data.getStateDod());

            Cell cell_17 = row.createCell(16, CellType.STRING);
            cell_17.setCellStyle(ratio_1dp);
            cell_17.setCellValue(data.getRatio());

            Cell cell_18 = row.createCell(17, CellType.STRING);
            cell_18.setCellStyle(rstyle);
            cell_18.setCellValue(data.getScheduleRatio());

            Cell cell_19 = row.createCell(18, CellType.STRING);
            cell_19.setCellStyle(rstyle);
            cell_19.setCellValue(data.getSpi());

            Cell cell_20 = row.createCell(19, CellType.STRING);
            cell_20.setCellStyle(spiSquadStyle);
            cell_20.setCellValue(data.getSpiPerSquad().doubleValue());

            Cell cell_21 = row.createCell(20, CellType.STRING);
            cell_21.setCellStyle(stateDurationStyle);
            cell_21.setCellValue(data.getDurationInSprint());

            Cell cell_22 = row.createCell(21, CellType.STRING);
            cell_22.setCellStyle(stateDurationStyle);
            cell_22.setCellValue(data.getPbiItemAgeInDays());

            rownum++;
        }
    }
}
