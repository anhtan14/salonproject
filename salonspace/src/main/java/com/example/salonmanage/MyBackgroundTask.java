package com.example.salonmanage;

import com.example.salonmanage.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyBackgroundTask {

    @Autowired private BookingRepository bookingRepository;
    @Scheduled(fixedRate = 1800000) // Runs every 1 second
    public void executeTask() {
        bookingRepository.checkStatus();
    }
}