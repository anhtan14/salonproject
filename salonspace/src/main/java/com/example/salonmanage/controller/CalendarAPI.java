package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.CalendarDTO;
import com.example.salonmanage.DTO.CalendarRequest;
import com.example.salonmanage.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarAPI {

    @Autowired
    CalendarService calendarService;

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public List<CalendarDTO> get(@RequestBody @Valid CalendarRequest calendarRequest) throws Exception{
        return  calendarService.getData(calendarRequest.getStart(),calendarRequest.getEnd(),calendarRequest.getBranch());
    }
}
