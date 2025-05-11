package com.example.salonmanage.DTO;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AuthRequest {
    @NotNull
     @Length(min = 5, max = 50)
    private String phone;

    @NotNull @Length(min = 3, max = 500)
    private String password;

    public AuthRequest() {

    }

    public AuthRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
