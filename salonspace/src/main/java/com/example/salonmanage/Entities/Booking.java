package com.example.salonmanage.Entities;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String date;
    @Column()
    private long discount;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private int payment;
    @Column(nullable = false)
    private long totalPrice;
    @Column(nullable = true)
    private Integer nhanvien;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "times_id")
    private Times times ;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name="branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "booking")
    private Collection<BookingDetail> bookingDetails;


    public Booking() {
    }

    public Booking(Integer ID, String date, long discount, int status, int payment, long totalPrice, Integer nhanvien, User user, Times times, Branch branch, Collection<BookingDetail> bookingDetails) {
        this.ID = ID;
        this.date = date;
        this.discount = discount;
        this.status = status;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.nhanvien = nhanvien;
        this.user = user;
        this.times = times;
        this.branch = branch;
        this.bookingDetails = bookingDetails;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(Integer nhanvien) {
        this.nhanvien = nhanvien;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Times getTimes() {
        return times;
    }

    public void setTimes(Times times) {
        this.times = times;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Collection<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(Collection<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
