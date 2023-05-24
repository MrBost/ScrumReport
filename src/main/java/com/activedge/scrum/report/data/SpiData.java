package com.activedge.scrum.report.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpiData {
    private String key;
    private BigDecimal spiPerSquad;
}
