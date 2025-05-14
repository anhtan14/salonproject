package com.example.salonmanage.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class registerDTO {
    private String otp;
    private String name;
    private String birthday;
    private String phone;
    private String pass;
    private String email;
}
