package com.example.salonmanage.service;

import com.example.salonmanage.Entities.Notification;
import com.example.salonmanage.Entities.User;

public interface NotificationService {
    Notification save(String text, User user);
}
