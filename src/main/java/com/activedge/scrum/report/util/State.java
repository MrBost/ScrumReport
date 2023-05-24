package com.activedge.scrum.report.util;

public enum State {
    APPROVED("Approved For Release"),
    CHANGEMANAGEMENT("Change Management"),
    CONTROLLEDLIVE("Controlled Live"),
    DEVELOPMENT("Development"),
    DEVELOPMENTCOMPLETED("Development Completed"),
    DEVELOPMENTONGOING("Development Ongoing"),
    DONE("Done"),
    NEW("New"),
    ONHOLD("On Hold"),
    SITCOMPLETED("SIT Completed"),
    SITONGOING("SIT On-going"),
    SYSTEMTESTING("System Testing"),
    UAT("UAT"),
    UATCOMPLETED("UAT Completed"),
    UATONGOING("UAT On-Going");


    private String value;

    State(String value) {
        this.value=value;
    }

    public String getValue(){return value;}

//    @Override
//    public String toString(){
//        return value;
//    }
}
