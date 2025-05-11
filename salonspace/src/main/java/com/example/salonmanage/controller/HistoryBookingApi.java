package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.BookingDTO;
import com.example.salonmanage.DTO.BookingHistoryDTO;
import com.example.salonmanage.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class HistoryBookingApi {
    @Autowired BookingService bookingService;

    @GetMapping("/history/{phone}")
    public List<BookingHistoryDTO> getUserBookingHistoryByPhone(@PathVariable String phone) {
        return bookingService.getUserBookingHistoryByPhone(phone);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/history/all")
    public List<BookingHistoryDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }
}