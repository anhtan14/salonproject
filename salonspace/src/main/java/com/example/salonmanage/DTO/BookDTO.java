package com.example.salonmanage.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String date;
    private Long totalPrice;
    private Integer nhanvien;
    private String user;
    private Integer time;
    private Integer branch;
    private String bankCode;

    @Override
    public String toString() {
        return "BookDTO{" +
                "date='" + date + '\'' +
                ", totalPrice=" + totalPrice +
                ", nhanvien=" + nhanvien +
                ", user='" + user + '\'' +
                ", time=" + time +
                ", branch=" + branch +
                '}';
    }
}
