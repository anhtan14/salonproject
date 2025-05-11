package com.example.salonmanage.service;

import com.example.salonmanage.DTO.CalendarDTO;

import java.util.List;

public interface CalendarService {

    List<CalendarDTO> getData(String start, String end, int branch) throws Exception;
}
