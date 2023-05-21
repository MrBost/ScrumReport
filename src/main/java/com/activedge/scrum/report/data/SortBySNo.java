package com.activedge.scrum.report.data;

import java.util.Comparator;

public class SortBySNo implements Comparator<OutputData> {
    @Override
    public int compare(OutputData o1, OutputData o2) {
        return Long.compare(o1.getSNo(),o2.getSNo());
    }
}
