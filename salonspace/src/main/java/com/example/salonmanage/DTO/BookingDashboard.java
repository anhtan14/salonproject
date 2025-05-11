package com.example.salonmanage.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDashboard {
    private int id;
    private String userName;
    private String date;
    private double cost;
}
