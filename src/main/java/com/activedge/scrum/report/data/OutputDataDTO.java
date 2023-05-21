package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Data;

@Data
public class OutputDataDTO {
    @ExcelCell(1)
    private String squad;
    @ExcelCell(2)
    private String assignedTo;
    @ExcelCell(3)
    private String squadTeam;
    @ExcelCell(4)
    private String iterationPath;
    @ExcelCell(5)
    private String id;
    @ExcelCell(6)
    private String workItemType;
    @ExcelCell(7)
    private String title;
    @ExcelCell(8)
    private String createdDate;
    @ExcelCell(9)
    private String activatedDate;
    @ExcelCell(10)
    private String expectedEndDate;
    @ExcelCell(11)
    private double durationInSprintWks;
    @ExcelCell(12)
    private String state;
    @ExcelCell(13)
    private String dod;
    @ExcelCell(14)
    private int stateValue;
    @ExcelCell(15)
    private int stateDod;
    @ExcelCell(16)
    private double ratio;
    @ExcelCell(17)
    private double scheduleRatio;
    @ExcelCell(18)
    private double spi;
    @ExcelCell(19)
    private double spiPerSquad;
    @ExcelCell(20)
    private int durationInSprint;
    @ExcelCell(21)
    private int pbiItemAgeInDays;
}
