package com.example.salonmanage.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name="branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double lat;

    private double lng;


    private String phone;

    @Column(nullable = false, length = 1028)
    @NotNull
    @Nationalized
    private String address;

    @ColumnDefault("1")
    private int status;


    public Branch() {

    }


    public Branch(Integer id, String name, double lat, double lng, String address, int status) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                '}';
    }
}
