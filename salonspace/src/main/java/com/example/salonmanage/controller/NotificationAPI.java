package com.example.salonmanage.controller;

import com.example.salonmanage.Entities.Notification;
import com.example.salonmanage.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "*")
public class NotificationAPI {

    @Autowired private NotificationRepository notificationRepository;

    @GetMapping("/{id}")
    public List<Notification> get(@PathVariable Integer id){
        return  notificationRepository.listAll(id);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> markRead(@PathVariable Integer id){
        notificationRepository.read(id);
        return ResponseEntity.ok("done");
    }
}
