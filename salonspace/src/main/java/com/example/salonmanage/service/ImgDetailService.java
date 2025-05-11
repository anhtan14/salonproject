package com.example.salonmanage.service;

import com.example.salonmanage.Entities.ImgDetail;
import com.example.salonmanage.repository.ImgDetailReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ImgDetailService {

    @Autowired
    ImgDetailReponsitory imgDetailReponsitory;
    @Autowired
    public ImgDetailService(ImgDetailReponsitory imgDetailRepository) {
        this.imgDetailReponsitory = imgDetailRepository;
    }

    public Collection<ImgDetail> getImgDetails() {
        return imgDetailReponsitory.findAll();
    }
}
