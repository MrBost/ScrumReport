package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DodData {
    @ExcelCell(1)
    private String dod;
    @ExcelCell(2)
    private int score;

}
