package com.example.salonmanage.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingHistoryDTO {
    private Integer id;
    private String date;
    private long discount;
    private int status;
    private int payment;
    private long totalPrice;
    private String nhanvienName;
    private SetTime time;
    private userDTO user;
    private BranchDTO branch;
}
