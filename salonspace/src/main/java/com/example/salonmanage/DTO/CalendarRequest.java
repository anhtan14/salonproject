package com.example.salonmanage.DTO;

import javax.validation.constraints.NotNull;

public class CalendarRequest {

    @NotNull
    private String start;

    @NotNull
    private String end;

    @NotNull
    private int branch;
    public CalendarRequest() {
    }

    public CalendarRequest(String start, String end, int branch) {
        this.start = start;
        this.end = end;
        this.branch = branch;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
