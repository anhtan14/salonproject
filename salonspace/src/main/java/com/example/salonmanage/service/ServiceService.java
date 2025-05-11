package com.example.salonmanage.service;

import com.example.salonmanage.Entities.ImgDetail;
import com.example.salonmanage.Entities.Service;

import java.util.List;

public interface ServiceService {
    public String saveImg(String img);

    public String saveImgDetail(String img, String id, int i);

    public List<String> updateImgDetail(String[] img,  Service service);

    public void removeImg(String img);
}
