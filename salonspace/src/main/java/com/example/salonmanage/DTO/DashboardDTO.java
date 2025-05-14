package com.example.salonmanage.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private int user;
    private int branch;
    private int service;
    private int employee;
    private long totalPrice;
    private int booking;
    private double increase;
    private List<BookingDashboard>  bookingDashboards;
}
