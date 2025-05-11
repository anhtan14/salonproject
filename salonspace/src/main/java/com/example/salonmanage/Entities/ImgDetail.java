package com.example.salonmanage.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "img_detail")
public class ImgDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String img;
    @ManyToOne
    @JoinColumn(name = "service_iD")
    @JsonBackReference
    private Service service;

    public ImgDetail() {
    }

    public ImgDetail( String img, Service service) {

        this.img = img;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


}

