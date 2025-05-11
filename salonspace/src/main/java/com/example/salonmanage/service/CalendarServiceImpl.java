package com.example.salonmanage.service;


import com.example.salonmanage.Entities.Booking;
import com.example.salonmanage.DTO.CalendarDTO;
import com.example.salonmanage.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<CalendarDTO> getData(String start, String end, int branch) throws Exception {
        List<Booking> bookings = new ArrayList<>();
        if (branch == 0) {
            bookings = bookingRepository.getDataCalendarWithNone(start, end);
        } else {
            bookings = bookingRepository.getDataCalendarWithBranch(start, end, branch);
        }
        List<CalendarDTO> calendarDTOList = new ArrayList<>();
        for (Booking b : bookings
        ) {
            if (b.getTimes() != null) {
                calendarDTOList.add(convertToDTO(b));
            }
        }
        return calendarDTOList;
    }

    private CalendarDTO convertToDTO(Booking booking) throws Exception {
        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setId(booking.getID());
        String sDate1 = booking.getDate();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        calendarDTO.setDate(date);
        calendarDTO.setTime(booking.getTimes().getTime());
        return calendarDTO;
    }
}
