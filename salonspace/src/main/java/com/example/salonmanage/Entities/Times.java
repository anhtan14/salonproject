package com.example.salonmanage.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "times")
public class Times {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String time;
    @OneToMany(mappedBy = "times")
    @JsonIgnore
    private List<Booking> booking = new ArrayList<>();

    public Times() {
    }

    public Times(Integer ID, String time, List<Booking> booking) {
        this.ID = ID;
        this.time = time;
        this.booking = booking;
    }

    public Times(String time) {
        this.time = time;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Times{" +
                "ID=" + ID +
                ", time='" + time + '\'' +
                ", booking=" + booking +
                '}';
    }
}
