package com.example.salonmanage.DTO;

import java.util.Date;

public class CommentDTO {
    private int id;
    private String text;
    private int parentID;
    private int user_id;
    private int service_id;
    private Date date;

    public CommentDTO() {
    }

    public CommentDTO(String text, int parentID, int user_id, int service_id, Date date) {
        this.text = text;
        this.parentID = parentID;
        this.user_id = user_id;
        this.service_id = service_id;
        this.date = date;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}