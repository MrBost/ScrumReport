package com.activedge.scrum.report.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.List;

public abstract class FinastraUtil<E extends Object>{
    protected SXSSFWorkbook wb;
    protected Sheet sh;
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
    private void fillHeader(String[] columns, String sheetName) {

        sh = wb.createSheet(sheetName);
        CellStyle headerStyle = getHeaderStyle();
        for (int rownum = 0; rownum < 1; rownum++) {
            Row row = sh.createRow(rownum);
            for (int cellnum = 0; cellnum < columns.length; cellnum++) {
                Cell cell = row.createCell(cellnum);
                cell.setCellValue(columns[cellnum]);
                cell.setCellStyle(headerStyle);
            }
        }
    }

    /**
     * @param columns
     * @param dataList
     * @return
     */
    public final SXSSFWorkbook exportExcel(String[] columns, List<E> dataList,Integer numberOfRowsPerSheet,String sheetName) {

        wb = new SXSSFWorkbook(100);
        int sheetNumber = 0;

//        for (List<E> list : output) {
            fillHeader(columns, sheetName);
            fillData(dataList);
            sheetNumber++;
//        }

        return wb;
    }

    /** @param dataList */
    abstract void fillData(List<E> dataList);
}
