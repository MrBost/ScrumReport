package com.activedge.scrum.report.utility;

import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.List;

public abstract class ExportUtility<E,D,F extends Object>{
    protected SXSSFWorkbook wb;
    protected Sheet sh;
    protected Sheet sh2;
    protected Sheet sh3;
    protected static final String EMPTY_VALUE = " ";

    /**
     * This method will return Style of Header Cell
     *
     * @return style
     */
    protected CellStyle getHeaderStyle() {

        XSSFFont fontBold = (XSSFFont) wb.createFont();
        fontBold.setColor(IndexedColors.BLUE.getIndex());
        fontBold.setFontHeightInPoints((short) 13);
        fontBold.setBold(true);
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(fontBold);
        return style;
    }

    /**
     * This method will return style for Normal Cell
     *
     * @return style
     */
    protected CellStyle getNormalStyle() {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    /** @param columns */
    private void fillHeader(String[] columns,String[] columns2,String[] columns3, String sheetName, String sheetName2,String sheetName3) {

        sh = wb.createSheet(sheetName);
        sh2 = wb.createSheet(sheetName2);
        sh3 = wb.createSheet(sheetName3);
        CellStyle headerStyle = getHeaderStyle();
        for (int rownum = 0; rownum < 1; rownum++) {
            Row row = sh.createRow(rownum);
            Row row2 = sh2.createRow(rownum);
            Row row3 = sh3.createRow(rownum);
            for (int cellnum = 0; cellnum < columns.length; cellnum++) {
                Cell cell = row.createCell(cellnum);
                cell.setCellValue(columns[cellnum]);
                cell.setCellStyle(headerStyle);
            }
            for (int cellnum = 0; cellnum < columns2.length; cellnum++) {
                Cell cell = row2.createCell(cellnum);
                cell.setCellValue(columns2[cellnum]);
                cell.setCellStyle(headerStyle);
            }
            for (int cellnum = 0; cellnum < columns3.length; cellnum++) {
                Cell cell = row3.createCell(cellnum);
                cell.setCellValue(columns3[cellnum]);
                cell.setCellStyle(headerStyle);
            }
        }
    }

    /**
     * @param columns
     * @param dataList
     * @return
     */
    public final SXSSFWorkbook exportExcel(String[] columns,String[] columns2,String[] columns3, List<E> dataList, List<D> dataList2,List<F> dataList3,
                                           Integer numberOfRowsPerSheet,String sheetName,String sheetName2, String sheetName3) {

//        List<List<E>> output = ListUtils.partition(dataList, numberOfRowsPerSheet);
//        List<List<E>> output2 = ListUtils.partition(dataList2, numberOfRowsPerSheet);
        wb = new SXSSFWorkbook(100);
        int sheetNumber = 0;

//        for (List<E> list : output) {
            fillHeader(columns, columns2,columns3, sheetName,sheetName2,sheetName3);
            fillData(dataList,dataList2,dataList3);
            sheetNumber++;
//        }

        return wb;
    }

    /** @param dataList */
    abstract void fillData(List<E> dataList,List<D> dataList2, List<F> dataList3);
}
