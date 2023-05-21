package com.activedge.scrum.report.data;

import lombok.Data;

@Data
public class OutputData {
    private String squad;
    private String assignedTo;
    private String squadTeam;
    private String iterationPath;
    private String id;
    private String workItemType;
    private String title;
    private String createdDate;
    private String activatedDate;
    private String expectedEndDate;
    private double durationInSprintWks;
    private String state;
    private String dod;
    private int stateValue;
    private int stateDod;
    private double ratio;
    private double scheduleRatio;
    private double spi;
    private double spiPerSquad;
    private int durationInSprint;
    private int pbiItemAgeInDays;
    private int sNo;


}
