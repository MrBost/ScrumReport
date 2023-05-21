package com.activedge.scrum.report.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public abstract class ExportUtil<E extends Object>{
    private static final int NON_REPTNG_COLS = 5;
    protected XSSFWorkbook wb;
    protected Sheet sh;

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

    protected void mergeRegion(int first, int last){
        for (int k = 0; k < NON_REPTNG_COLS; k++) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(first,
                        last, k, k);
                sh.addMergedRegion(cellRangeAddress);
            }
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

    protected CellStyle getWrapStyle() {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        return style;
    }

    protected void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(7, 40 * 256);
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
    public final XSSFWorkbook exportExcel(String[] columns, List<E> dataList,String sheetName) {

        wb = new XSSFWorkbook();

            fillHeader(columns, sheetName);
            fillData(dataList);
            setColumnWidth(sh);
            merge(sh);

        return wb;
    }
    private void merge(Sheet sh){
        System.out.println("sheet size "+sh.getPhysicalNumberOfRows());
        int first = 1;
        int last = 1;
        int i = 1;
        while (i < sh.getPhysicalNumberOfRows()) {
            first = i;
            last = i;
            for (int j = i + 1; j < sh.getPhysicalNumberOfRows(); j++) {
                String cell1 = sh.getRow(i)!=null ? sh.getRow(i).getCell(1).toString() : "";
                String cell2 = sh.getRow(j)!=null ? sh.getRow(j).getCell(1).toString() :"";
                if (cell1.equals(cell2)) {
                    last = j;
                    System.out.println(cell1 +" >>> "+ cell2 +" first "+first+" last "+last);
                }
            }
            try{
                mergeRegion(first,last);
            }catch (Exception ex){
                System.out.println("cannot merge a single cell");
            }
            i = last + 1;

        }

    }


    /** @param dataList */
    abstract void fillData(List<E> dataList);
}
