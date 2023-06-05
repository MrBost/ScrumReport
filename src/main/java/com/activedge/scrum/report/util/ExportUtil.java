package com.activedge.scrum.report.util;

import org.apache.poi.hpsf.Decimal;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.text.DecimalFormat;
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
        XSSFFont fontBold = wb.createFont();
        fontBold.setColor(IndexedColors.WHITE.getIndex());
        fontBold.setFontName("Calibri");
        fontBold.setFontHeightInPoints((short) 8);
        fontBold.setBold(true);
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new Color(0,32,96)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setFont(fontBold);
        style.setWrapText(true);
        return style;
    }
    protected CellStyle getPvStyle() {
        XSSFFont fontBold = wb.createFont();
        fontBold.setFontName("Calibri");
        fontBold.setFontHeightInPoints((short) 8);
        fontBold.setBold(true);
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new Color(224,176,132)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(fontBold);
        return style;
    }

    protected CellStyle getStateStyle(int red, int green, int blue) {
        XSSFFont fontBold = wb.createFont();
        fontBold.setFontName("Calibri");
        fontBold.setFontHeightInPoints((short) 8);
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new Color(red,green,blue)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(fontBold);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }

    protected void mergeRegion(int first, int last){
        for (int k = 0; k < NON_REPTNG_COLS; k++) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(first,last, k, k);
                sh.addMergedRegion(cellRangeAddress);
            }
    }

    /**
     * This method will return style for Normal Cell
     *
     * @return style
     */
    protected CellStyle getNormalStyle() {
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }

    protected static CellStyle getRatio(XSSFWorkbook wb) {
        DataFormat df = wb.createDataFormat();
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(df.getFormat("0.00"));
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style ;
    }

    protected static CellStyle getRatio_1dp(XSSFWorkbook wb) {
        DataFormat df = wb.createDataFormat();
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(df.getFormat("0.0"));
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style ;
    }
    protected CellStyle getRatio_1dpAlternatingRowStyle(XSSFWorkbook wb) {
        DataFormat df = wb.createDataFormat();
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(180,198,231)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        style.setDataFormat(df.getFormat("0.0"));
        return style;
    }

    protected static CellStyle getDateFormat(XSSFWorkbook wb){
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(
                createHelper.createDataFormat().getFormat("dd-MMM-yy"));
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }
    protected CellStyle getDateAlternatingRowStyle(XSSFWorkbook wb) {
        CreationHelper createHelper = wb.getCreationHelper();
        XSSFCellStyle style = wb.createCellStyle();
        style.setDataFormat(
                createHelper.createDataFormat().getFormat("dd-MMM-yy"));
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(180,198,231)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }
    protected CellStyle getRatioAlternatingRowStyle(XSSFWorkbook wb) {
        DataFormat df = wb.createDataFormat();
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(180,198,231)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        style.setDataFormat(df.getFormat("0.00"));
        return style;
    }
    protected CellStyle getStateStyle() {
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }
    protected CellStyle getSpiSquadStyle() {
        DataFormat df = wb.createDataFormat();
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new Color(0,32,96)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        style.setDataFormat(df.getFormat("0.00"));
        return style;
    }

    protected CellStyle getAlternatingRowStyle() {
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new Color(180,198,231)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }

    protected CellStyle getStateAlternatingRowStyle() {
        XSSFFont font = wb.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 8);
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new Color(180,198,231)));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }
    protected CellStyle getWrapStyle() {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        return style;
    }
    protected void setColumnWidth(Sheet sheet) {
        sheet.setColumnWidth(0,3 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 6 * 256);
        sheet.setColumnWidth(6, 5 * 256);
        sheet.setColumnWidth(7, 20 * 256);
        sheet.setColumnWidth(8, 10 * 256);
        sheet.setColumnWidth(9, 10 * 256);
        sheet.setColumnWidth(10, 10 * 256);
        sheet.setColumnWidth(11, 10 * 256);
        sheet.setColumnWidth(12, 15 * 256);
        sheet.setColumnWidth(13, 10 * 256);
        sheet.setColumnWidth(14, 10 * 256);
        sheet.setColumnWidth(15, 10 * 256);
        sheet.setColumnWidth(16, 10 * 256);
        sheet.setColumnWidth(17, 10 * 256);
        sheet.setColumnWidth(18, 8 * 256);
        sheet.setColumnWidth(19, 8 * 256);
        sheet.setColumnWidth(20, 10 * 256);

    }

    /** @param columns */
    private void fillHeader(String[] columns, String sheetName) {

        sh = wb.createSheet(sheetName);
        CellStyle headerStyle = getHeaderStyle();
        for (int rownum = 0; rownum < 1; rownum++) {
            Row row = sh.createRow(1);
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
                CellRangeAddress cellRangeAddress = new CellRangeAddress(first,last, 19, 19);
                sh.addMergedRegion(cellRangeAddress);
            }catch (Exception ex){
                System.out.println("cannot merge a single cell");
            }
            i = last + 1;

        }

    }


    /** @param dataList */
    abstract void fillData(List<E> dataList);
}
