package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StateData {
    @ExcelCell(1)
    private String state;
    @ExcelCell(2)
    private int score;

}
