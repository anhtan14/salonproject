package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.BookingDashboard;
import com.example.salonmanage.DTO.ChartDTO;
import com.example.salonmanage.DTO.ChartRequest;
import com.example.salonmanage.DTO.DashboardDTO;
import com.example.salonmanage.Entities.Booking;
import com.example.salonmanage.excel.ChartExcelExporter;
import com.example.salonmanage.repository.BookingRepository;
import com.example.salonmanage.repository.BranchRepository;
import com.example.salonmanage.repository.ServiceRepository;
import com.example.salonmanage.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardAPI {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private userRepository userRepository;

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public DashboardDTO get() {
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setBranch(branchRepository.countAllWithNotRemove());
        dashboardDTO.setService(serviceRepository.countAllWithNotRemove());
        dashboardDTO.setUser(userRepository.countAllWithNotRemove("ROLE_CUSTOMER"));
        dashboardDTO.setEmployee(userRepository.countAllWithNotRemove("ROLE_STAFF"));
        dashboardDTO.setTotalPrice(bookingRepository.sumAmount());
        dashboardDTO.setBooking(bookingRepository.countAllWithNotRemove());
        int count30DayBefore = bookingRepository.countAllWithNotRemove30DayBefore();
        if (count30DayBefore == 0) {
            dashboardDTO.setIncrease((bookingRepository.countAllWithNotRemove()) * 100);
        } else {
            dashboardDTO.setIncrease((bookingRepository.countAllWithNotRemove() - bookingRepository.countAllWithNotRemove30DayBefore()) / bookingRepository.countAllWithNotRemove30DayBefore() * 100);
        }
        List<Booking> booking = bookingRepository.getTop10();
        List<BookingDashboard> bookingDashboards = new ArrayList<>();
        String name;
        for (int i = 0; i < booking.size(); i++) {
            if (booking.get(i).getUser() == null) {
                name = "khánh hàng";
            } else {
                name = booking.get(i).getUser().getName();
            }
            bookingDashboards.add(new BookingDashboard(booking.get(i).getID(), name, booking.get(i).getDate(), booking.get(i).getTotalPrice()));
        }
        dashboardDTO.setBookingDashboards(bookingDashboards);
        return dashboardDTO;
    }

    @GetMapping("/chart")
    @RolesAllowed("ROLE_ADMIN")
    public List<ChartDTO> getChart() {
        return bookingRepository.getDataChart();
    }

    @PostMapping("/chart")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> getChartWithDate(@RequestBody ChartRequest chartRequest) {
        if (chartRequest.getType() == 1 || chartRequest.getType() == 2 || chartRequest.getType() == 3) {
            LocalDate dateStart = LocalDate.parse(chartRequest.getDateStart());
            LocalDate dateEnd = LocalDate.parse(chartRequest.getDateEnd());
            if (chartRequest.getType() == 1) {
                if (dateStart.getYear() != dateEnd.getYear() && dateStart.getYear() < dateEnd.getYear()) {
                    if (chartRequest.getBranch() == 0) {
                        return ResponseEntity.ok().body(bookingRepository.getDataChartWithDateYear(chartRequest.getDateStart(), chartRequest.getDateEnd()));
                    } else {
                        return ResponseEntity.ok().body(bookingRepository.getDataChartWithDateYearBranch(chartRequest.getDateStart(), chartRequest.getDateEnd(), chartRequest.getBranch()));
                    }
                } else {
                    return ResponseEntity.ok().body("year");
                }

            }
            if (chartRequest.getType() == 2) {
                if (dateStart.getMonthValue() != dateEnd.getMonthValue() && dateStart.getYear() == dateEnd.getYear()) {
                    if (chartRequest.getBranch() == 0) {
                        return ResponseEntity.ok().body(bookingRepository.getDataChartWithDateMonth(chartRequest.getDateStart(), chartRequest.getDateEnd()));
                    } else {
                        return ResponseEntity.ok().body(bookingRepository.getDataChartWithDateMonthBranch(chartRequest.getDateStart(), chartRequest.getDateEnd(), chartRequest.getBranch()));
                    }
                } else {
                    return ResponseEntity.ok().body("month");
                }
            }
            if (chartRequest.getType() == 3) {
                if (dateStart.getMonthValue() == dateEnd.getMonthValue() && dateStart.getYear() == dateEnd.getYear()) {
                    if (chartRequest.getBranch() == 0) {
                        return ResponseEntity.ok().body(bookingRepository.getDataChartWithDate(chartRequest.getDateStart(), chartRequest.getDateEnd()));
                    } else {
                        return ResponseEntity.ok().body(bookingRepository.getDataChartWithDateBranch(chartRequest.getDateStart(), chartRequest.getDateEnd(), chartRequest.getBranch()));
                    }
                } else {
                    return ResponseEntity.ok().body("day");
                }
            }
            return ResponseEntity.ok().body(bookingRepository.getDataChartWithDate(chartRequest.getDateStart(), chartRequest.getDateEnd()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/chart/download")
//    @RolesAllowed("ROLE_ADMIN")
    public void getChartDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        LocalDateTime currentTime = LocalDateTime.now();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + "Report_" + currentTime.getMonth() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<ChartDTO> chartDTOList = bookingRepository.getDataChart();
        ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
        excelExporter.export(response);
    }

    @GetMapping("/chart/{start}/{end}/{type}/{branch}")
//    @RolesAllowed("ROLE_ADMIN")
    public void getChartDownloadMulty(HttpServletResponse response, @PathVariable String start, @PathVariable String end, @PathVariable Integer type, @PathVariable Integer branch) throws IOException {
        if (type == 1 || type == 2 || type == 3) {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" + "Report_" + start + "-" + end + ".xlsx";
            response.setHeader(headerKey, headerValue);
            List<ChartDTO> chartDTOList = new ArrayList<>();
            LocalDate dateStart = LocalDate.parse(start);
            LocalDate dateEnd = LocalDate.parse(end);
            if (type == 1) {
                if (dateStart.getYear() != dateEnd.getYear() && dateStart.getYear() < dateEnd.getYear()) {
                    if (branch == 0) {
                        chartDTOList = bookingRepository.getDataChartWithDateYear(start, end);
                        ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
                        excelExporter.export(response);
                    } else {
                        chartDTOList = bookingRepository.getDataChartWithDateYearBranch(start, end, branch);
                        ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
                        excelExporter.export(response);
                    }
                }
            }
            if (type == 2) {
                if (dateStart.getMonthValue() != dateEnd.getMonthValue() && dateStart.getYear() == dateEnd.getYear()) {
                    if (branch == 0) {
                        chartDTOList = bookingRepository.getDataChartWithDateMonth(start, end);
                        ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
                        excelExporter.export(response);
                    } else {
                        chartDTOList = bookingRepository.getDataChartWithDateMonthBranch(start, end, branch);
                        ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
                        excelExporter.export(response);
                    }
                }
            }
            if (type == 3) {
                if (dateStart.getMonthValue() == dateEnd.getMonthValue() && dateStart.getYear() == dateEnd.getYear()) {
                    if (branch == 0) {
                        chartDTOList = bookingRepository.getDataChartWithDate(start, end);
                        ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
                        excelExporter.export(response);
                    } else {
                        chartDTOList = bookingRepository.getDataChartWithDateBranch(start, end, branch);
                        ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
                        excelExporter.export(response);
                    }
                }
            }
            chartDTOList = bookingRepository.getDataChartWithDate(start, end);
            ChartExcelExporter excelExporter = new ChartExcelExporter(chartDTOList);
            excelExporter.export(response);
        }

    }
}
