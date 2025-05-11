package com.example.salonmanage.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nationalized
    private String text;

    private Date date;

    private int parentID;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @ManyToOne()
    @JoinColumn(name = "service_id")
    @JsonIgnore
    private Service service;

    public Comment() {
    }

    public Comment(int id, String text, Date date, int parentID, User user, Service service) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.parentID = parentID;
        this.user = user;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    @Override
    public String toString() {
        return "comment{" +
                "id=" + id +
                ", parentID=" + parentID +
                ", user=" + user +
                ", service=" + service +
                '}';
    }
}