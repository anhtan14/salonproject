package com.example.salonmanage.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingDTO {
    private Integer id;
  private String date;
    private long discount;
    private int status;
    private int payment;
    private long totalPrice;
    private Integer nhanvien;
    private userDTO user;
    private BranchDTO branch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(Integer nhanvien) {
        this.nhanvien = nhanvien;
    }

    public userDTO getUser() {
        return user;
    }

    public void setUser(userDTO user) {
        this.user = user;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }
}
