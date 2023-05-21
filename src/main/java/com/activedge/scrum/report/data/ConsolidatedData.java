package com.activedge.scrum.report.data;

import com.poiji.annotation.ExcelCell;
import lombok.Data;

@Data
public class ConsolidatedData {
    private String accountName;
    private String itemKey;
    private Number amount;
    private String sign;
    private String comment;
}
