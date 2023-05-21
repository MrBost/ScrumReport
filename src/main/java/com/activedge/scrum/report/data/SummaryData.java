package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Data;

@Data
public class SummaryData {
    @ExcelCell(0)
    private String name;
    @ExcelCell(1)
    private String modified;
    @ExcelCell(2)
    private String modifiedBy;

}
