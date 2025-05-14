package com.example.salonmanage.DTO;

import java.util.Date;

public class CalendarDTO {

    private int id;
    private Date date;
    private String time;

    public CalendarDTO() {
    }

    public CalendarDTO(int id, Date date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
