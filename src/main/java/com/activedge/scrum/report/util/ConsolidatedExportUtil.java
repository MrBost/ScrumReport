//package com.activedge.scrum.report.util;
//
//import com.activedge.scrum.report.data.ConsolidatedData;
//import com.activedge.scrum.report.data.FailedData;
//import com.activedge.scrum.report.data.MatchAdviceData;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//
//import java.util.List;
//
//public class ConsolidatedExportUtil extends ExportUtil<MatchAdviceData,ConsolidatedData, FailedData> {
//
//    @Override
//    void fillData(List<MatchAdviceData> dataList, List<ConsolidatedData> dataList2,List<FailedData> dataList3) {
//        CellStyle normalStyle = getNormalStyle();
//        CellStyle cs = getAmountFormat(wb);
//        int rownum = 1;
//        int rownum2 = 1;
//        int rownum3 = 1;
//
//        for (MatchAdviceData rev : dataList) {
//            Row row = sh.createRow(rownum);
//            Number amount = rev.getAmountNum();
//            Number checkBal = rev.getChkBal();
//            if(amount != null || checkBal!= null ){
//                Cell cell_0 = row.createCell(0, CellType.STRING);
//                cell_0.setCellStyle(normalStyle);
//                cell_0.setCellValue(rev.getTlmCode());
//
//                Cell cell_1 = row.createCell(1, CellType.STRING);
//                cell_1.setCellStyle(normalStyle);
//                cell_1.setCellValue(rev.getOwner());
//
//                Cell cell_2 = row.createCell(2, CellType.STRING);
//                cell_2.setCellStyle(normalStyle);
//                cell_2.setCellValue(rev.getAccountName());
//
//                Cell cell_2a = row.createCell(3, CellType.STRING);
//                cell_2a.setCellStyle(normalStyle);
//                cell_2a.setCellValue(rev.getLedgerAccount());
//
//                Cell cell_3 = row.createCell(4, CellType.STRING);
//                cell_3.setCellStyle(normalStyle);
//                cell_3.setCellValue(rev.getSolId());
//
//                Cell cell_4 = row.createCell(5, CellType.STRING);
//                cell_4.setCellStyle(normalStyle);
//                cell_4.setCellValue(rev.getCluster());
//
//                Cell cell_5 = row.createCell(6, CellType.STRING);
//                cell_5.setCellStyle(normalStyle);
//                cell_5.setCellValue(rev.getItemKey());
//
//                Cell cell_6 = row.createCell(7, CellType.STRING);
//                cell_6.setCellStyle(normalStyle);
//                cell_6.setCellValue(rev.getSign());
//
//                Cell cell_7 = row.createCell(8, CellType.STRING);
//                cell_7.setCellStyle(normalStyle);
//                cell_7.setCellValue(rev.getType());
//
//                Cell cell_8 = row.createCell(9, CellType.STRING);
//                cell_8.setCellStyle(normalStyle);
//                cell_8.setCellValue(rev.getValueDate());
//
//                Cell cell_9 = row.createCell(10, CellType.STRING);
//                cell_9.setCellStyle(normalStyle);
//                cell_9.setCellValue(rev.getPostDate());
//
//                Cell cell_10 = row.createCell(11, CellType.STRING);
//                cell_10.setCellStyle(cs);
//                cell_10.setCellValue(rev.getEscalationLevel());
//
//                Cell cell_11 = row.createCell(12, CellType.STRING);
//                cell_11.setCellStyle(normalStyle);
//                cell_11.setCellValue(rev.getAge());
//
//                Cell cell_12 = row.createCell(13, CellType.STRING);
//                cell_12.setCellStyle(normalStyle);
//                cell_12.setCellValue(rev.getLifespan());
//
//                Cell cell_13 = row.createCell(14, CellType.STRING);
//                cell_13.setCellStyle(normalStyle);
//                cell_13.setCellValue(rev.getAgeAfterLifespan());
//
//                Cell cell_14 = row.createCell(15, CellType.STRING);
//                cell_14.setCellStyle(normalStyle);
//                cell_14.setCellValue(rev.getCurrency());
//
//                Cell cell_17 = row.createCell(16, CellType.STRING);
//                cell_17.setCellStyle(cs);
//                cell_17.setCellValue(rev.getAmountNum().doubleValue());
//
//                Cell cell_18 = row.createCell(17, CellType.STRING);
//                cell_18.setCellStyle(cs);
//                cell_18.setCellValue(rev.getChkBal().doubleValue());
//
//                Cell cell_19 = row.createCell(18, CellType.STRING);
//                cell_19.setCellStyle(normalStyle);
//                cell_19.setCellValue(rev.getRef1());
//
//                Cell cell_20 = row.createCell(19, CellType.STRING);
//                cell_20.setCellStyle(normalStyle);
//                cell_20.setCellValue(rev.getNarration());
//
//                Cell cell_21 = row.createCell(20, CellType.STRING);
//                cell_21.setCellStyle(normalStyle);
//                cell_21.setCellValue(rev.getRemarks());
//
//                Cell cell_22 = row.createCell(21, CellType.STRING);
//                cell_22.setCellStyle(normalStyle);
//                cell_22.setCellValue(rev.getOriginTranId());
//
//                Cell cell_23 = row.createCell(22, CellType.STRING);
//                cell_23.setCellStyle(normalStyle);
//                cell_23.setCellValue(rev.getOriginUserId());
//
//                Cell cell_24 = row.createCell(23, CellType.STRING);
//                cell_24.setCellStyle(normalStyle);
//                cell_24.setCellValue(rev.getComment());
//
//                Cell cell_25 = row.createCell(24, CellType.STRING);
//                cell_25.setCellStyle(normalStyle);
//                cell_25.setCellValue(rev.getProbableMatch());
//
//                Cell cell_26 = row.createCell(25, CellType.STRING);
//                cell_26.setCellStyle(normalStyle);
//                cell_26.setCellValue(rev.getUser());
//            }
//
//            rownum++;
//        }
//        for (ConsolidatedData rev : dataList2) {
//            Row row = sh2.createRow(rownum2);
//            Number amount = rev.getAmount();
//            if(amount != null){
//                Cell cell_0 = row.createCell(0, CellType.STRING);
//                cell_0.setCellStyle(normalStyle);
//                cell_0.setCellValue(rev.getAccountName());
//
//                Cell cell_1 = row.createCell(1, CellType.STRING);
//                cell_1.setCellStyle(normalStyle);
//                cell_1.setCellValue(rev.getItemKey());
//
//                Cell cell_2 = row.createCell(2, CellType.STRING);
//                cell_2.setCellStyle(cs);
//                cell_2.setCellValue(rev.getAmount().doubleValue());
//
//                Cell cell_3 = row.createCell(3, CellType.STRING);
//                cell_3.setCellStyle(normalStyle);
//                cell_3.setCellValue(rev.getSign());
//
//                Cell cell_4 = row.createCell(4, CellType.STRING);
//                cell_4.setCellStyle(normalStyle);
//                cell_4.setCellValue(rev.getComment());
//            }
//
//
//            rownum2++;
//        }
//        for (FailedData rev : dataList3) {
//            Row row = sh3.createRow(rownum3);
//
//            Cell cell_0 = row.createCell(0, CellType.STRING);
//            cell_0.setCellStyle(normalStyle);
//            cell_0.setCellValue(rev.getUser());
//
//            rownum3++;
//        }
//    }
//
//    public static CellStyle getAmountFormat(SXSSFWorkbook wb) {
//        DataFormat df = wb.createDataFormat();
//        CellStyle cs = wb.createCellStyle();
//        cs.setDataFormat(df.getFormat("##,###,##0.00"));
//        return cs;
//    }
//}
