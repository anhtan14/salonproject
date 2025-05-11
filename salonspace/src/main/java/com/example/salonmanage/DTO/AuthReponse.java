package com.example.salonmanage.DTO;

public class AuthReponse {
    private int id;
    private String phone;
    private String name;
    private String img;

    private String accessToken;
    private String birthday;
    private String email;

    public AuthReponse() {
    }

    public AuthReponse(String phone, String name, String img, int id, String accessToken, String birthday, String email) {
        this.phone = phone;
        this.name = name;
        this.img = img;
        this.id = id;
        this.accessToken = accessToken;
        this.birthday = birthday;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
