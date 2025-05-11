package com.example.salonmanage.service;

import com.example.salonmanage.Entities.Notification;
import com.example.salonmanage.Entities.User;
import com.example.salonmanage.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class NotificationServiceImpl implements   NotificationService{

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public Notification save(String text, User user){
        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setUser(user);
        notification.setText(text);
        notification.setStatus(0);
        return notificationRepository.save(notification);
    }
}
