package com.example.salonmanage.controller;


import com.example.salonmanage.DTO.BookingHistoryDTO;
import com.example.salonmanage.Entities.Branch;
import com.example.salonmanage.Entities.Service;
import com.example.salonmanage.Entities.User;
import com.example.salonmanage.Entities.event;
import com.example.salonmanage.excel.*;
import com.example.salonmanage.repository.BranchRepository;
import com.example.salonmanage.repository.EventRepository;
import com.example.salonmanage.repository.ServiceRepository;
import com.example.salonmanage.repository.userRepository;
import com.example.salonmanage.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/download")
@CrossOrigin(origins = "*")
public class DownloadAPI {

    @Autowired private BookingService bookingService;
    @Autowired private userRepository userRepository;
    @Autowired private BranchRepository branchRepository;
    @Autowired private ServiceRepository serviceRepository;
    @Autowired private EventRepository eventRepository;
    @GetMapping("/booking")
    public void getBookingDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + "Report_booking.xlsx";
        response.setHeader(headerKey, headerValue);
        List<BookingHistoryDTO> chartDTOList = bookingService.getAllBookings();
        BookingExcelExporter excelExporter = new BookingExcelExporter(chartDTOList);
        excelExporter.export(response);
    }

    @GetMapping("/user")
    public void getUserDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + "Report_user.xlsx";
        response.setHeader(headerKey, headerValue);
        List<User> chartDTOList = userRepository.findAll();
        UserExcelExporter excelExporter = new UserExcelExporter(chartDTOList);
        excelExporter.export(response);
    }

    @GetMapping("/branch")
    public void getBranchDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + "Report_branch.xlsx";
        response.setHeader(headerKey, headerValue);
        List<Branch> chartDTOList = branchRepository.findAllWithNotRemoveAdmin();
        BranchExcelExporter excelExporter = new BranchExcelExporter(chartDTOList);
        excelExporter.export(response);
    }

    @GetMapping("/service")
    public void getServiceDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + "Report_service.xlsx";
        response.setHeader(headerKey, headerValue);
        List<Service> chartDTOList = serviceRepository.findAllWithNotRemoveAdmin();
        ServiceExcelExporter excelExporter = new ServiceExcelExporter(chartDTOList);
        excelExporter.export(response);
    }

    @GetMapping("/event")
    public void getEventDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + "Report_event.xlsx";
        response.setHeader(headerKey, headerValue);
        List<event> chartDTOList = eventRepository.findAll();
        EventExcelExporter excelExporter = new EventExcelExporter(chartDTOList);
        excelExporter.export(response);
    }
}
