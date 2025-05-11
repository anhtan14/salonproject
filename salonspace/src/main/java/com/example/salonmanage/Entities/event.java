package com.example.salonmanage.Entities;

import javax.persistence.*;

@Entity
@Table(name = "event")
public class event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer discount;
    private String content;
    private String img;
    private String date;
    private int status;


    public event() {
    }

    public event(int id, Integer discount, String content, String img, String date, int status) {
        this.id = id;
        this.discount = discount;
        this.content = content;
        this.img = img;
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
