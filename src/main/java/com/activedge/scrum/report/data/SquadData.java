package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class SquadData {
    @ExcelCell(0)
    private int sNo;
    @ExcelCell(1)
    private String squad;
    @ExcelCell(2)
    private String assignedTo;
    @ExcelCell(3)
    private String squadTeam;
    @ExcelCell(4)
    private String activatedDate;
    @ExcelCell(5)
    private String expectedEndDate;
}
