package com.example.salonmanage.service;

import com.example.salonmanage.Entities.ImgDetail;
import com.example.salonmanage.repository.ImgDetailReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.salonmanage.Entities.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ImgDetailReponsitory imgDetailReponsitory;

    @Autowired
    private ServiceService serviceService;
    @Override
    public String saveImg(String img){
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTimeString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String[] strings = img.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String pathfile = dateTimeString + "." + extension;
        fileStorageService.storeFile(new ByteArrayInputStream(data), pathfile);
        return pathfile;
    }

    @Override
    public String saveImgDetail(String img,String id, int i){
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTimeString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String[] strings = img.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String uniqueImageName = UUID.randomUUID().toString();
        String pathfile = uniqueImageName+"service-id"+id+"-"+String.valueOf(i)+"."+extension;
        fileStorageService.storeFile(new ByteArrayInputStream(data), pathfile);
        return pathfile;
    }

    @Override
    public List<String> updateImgDetail(String[] img,  Service service){
//        List<ImgDetail> imgDetails = new ArrayList<>(service.getImgDetails());
        List<ImgDetail> imgDetailsWithServiceId = imgDetailReponsitory.findByServiceId(service.getId());

        List<Integer> listImg = new ArrayList<Integer>();
        List<String> listImgDetail = new ArrayList<String>();
        for (String element : img) {
            if (!element.startsWith("data:image")) {
                for (int i = 0; i < imgDetailsWithServiceId.size(); i++) {
                    if (element.equals(imgDetailsWithServiceId.get(i).getImg())) {
                        listImg.add(imgDetailsWithServiceId.get(i).getId());
                    }
                }
            }else{
                listImgDetail.add(serviceService.saveImgDetail(element,"update",service.getId()));
            }
        }
        int flag;
        for (int i = 0; i < imgDetailsWithServiceId.size(); i++) {
            flag = 0;
            for (int j = 0; j < listImg.size(); j++) {
                if(imgDetailsWithServiceId.get(i).getId() == listImg.get(j)){
                    flag = flag + 1;
                }
            }
            if(flag ==0){
                serviceService.removeImg(imgDetailsWithServiceId.get(i).getImg());
               imgDetailReponsitory.delete(imgDetailsWithServiceId.get(i));
            }
        }
//        for (String imgString : img) {
//            if (imgString.startsWith("data:image")) {
//
//            }
//        }
        return listImgDetail;
    }

    @Override
    public void removeImg(String img){
        String[] pathSegments = img.split("/");
        fileStorageService.removeFile(pathSegments[pathSegments.length - 1]);
    }
}
