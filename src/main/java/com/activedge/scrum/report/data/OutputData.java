package com.activedge.scrum.report.data;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OutputData {
    private String squad;
    private String assignedTo;
    private String squadTeam;
    private String iterationPath;
    private long id;
    private String workItemType;
    private String title;
    private LocalDate createdDate;
    private LocalDate activatedDate;
    private LocalDate expectedEndDate;
    private double durationInSprintWks;
    private String state;
    private String dod;
    private int stateValue;
    private int stateDod;
    private double ratio;
    private double scheduleRatio;
    private double spi;
    private BigDecimal spiPerSquad;
    private long durationInSprint;
    private long pbiItemAgeInDays;
    private int sNo;
    private LocalDate stateChangeDate;


}
