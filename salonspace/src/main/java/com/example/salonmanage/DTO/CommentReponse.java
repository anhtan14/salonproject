package com.example.salonmanage.DTO;

import java.util.Date;
import java.util.List;

public class CommentReponse {
    private int id;
    private String text;
    private Date date;
    private int parentID;
    private int user_id;
    private String img;
    private String name;

    public CommentReponse() {
    }

    public CommentReponse(int id, String text, Date date, int parentID, int user_id, String img, String name) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.parentID = parentID;
        this.user_id = user_id;
        this.img = img;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}