package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReportData {
    @ExcelCell(1)
    private String iterationPath;
    @ExcelCell(2)
    private String assignedTo;
    @ExcelCell(3)
    private String id;
    @ExcelCell(4)
    private String title;
    @ExcelCell(5)
    private String createdDate;
    @ExcelCell(6)
    private String activatedDate;
    @ExcelCell(7)
    private String state;
    @ExcelCell(8)
    private String pbiDod;
    private String squad;
}
