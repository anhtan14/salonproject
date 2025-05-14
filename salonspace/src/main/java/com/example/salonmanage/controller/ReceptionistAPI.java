package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.BookingHistoryDTO;
import com.example.salonmanage.DTO.ReceptionistDTO;
import com.example.salonmanage.Entities.*;
import com.example.salonmanage.repository.*;
import com.example.salonmanage.service.BookingService;
import com.example.salonmanage.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/receptionist")
@CrossOrigin(origins = "*")
public class ReceptionistAPI {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private userRepository userReponsitory;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private TimesRepository timesRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;

    public ReceptionistAPI(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<Times> getTime() {
        return timesRepository.findAll();
    }

    @RolesAllowed("ROLE_RECEPTIONIST")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ReceptionistDTO receptionistDTO) {
        List<BookingDetail> bookingDetails = new ArrayList<>();

        User receptionist = userReponsitory.findById(receptionistDTO.getIdReceptionist()).get();
        User user = new User();
        boolean checkUser = false;
        if (userReponsitory.existsByPhone(receptionistDTO.getPhone())) {
            checkUser = true;
            user = userReponsitory.findByPhone(receptionistDTO.getPhone()).get();
        }
        for (int i = 0; i < receptionistDTO.getServiceId().size(); i++) {
            if (checkUser == true && serviceRepository.existsById(receptionistDTO.getServiceId().get(i)) && serviceRepository.getById(receptionistDTO.getServiceId().get(i)).getStatus() == 1) {
                BookingDetail bookingDetail = new BookingDetail();
                bookingDetail.setStatus(1);
                bookingDetail.setUser(user);
                bookingDetail.setService(serviceRepository.findServiceById(receptionistDTO.getServiceId().get(i)));
                bookingDetails.add(bookingDetail);
            }
        }
        Booking booking = new Booking();
        booking.setTotalPrice(receptionistDTO.getTotal());
        booking.setBranch(receptionist.getBranch());
        booking.setStatus(0);
        if (checkUser) {
            booking.setUser(user);
        }
        booking.setPayment(0);
        booking.setDiscount(1);
        booking.setDate(receptionistDTO.getDate());
        booking.setTimes(timesRepository.findById(receptionistDTO.getTime()).get());
        Booking newBooking1 = bookingRepository.save(booking);
        if (checkUser == true) {
            for (BookingDetail b : bookingDetails
            ) {
                b.setBooking(newBooking1);
                bookingDetailRepository.save(b);
            }
        }
        return ResponseEntity.ok("done");
    }

    @RolesAllowed("ROLE_RECEPTIONIST")
    @GetMapping("/status/{id}")
    public ResponseEntity<?> setStatus(@PathVariable Integer id) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setStatus(1);
            bookingRepository.save(booking);
            if (booking.getUser() != null) {
                Notification notification = notificationService.save("Đơn hàng " + booking.getID().toString() + " đã được hoàn thành", booking.getUser());
                messagingTemplate.convertAndSend("/topic/booking/" + booking.getUser().getId().toString(), notification);
            }
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @RolesAllowed("ROLE_RECEPTIONIST")
    @GetMapping("/accept/{id}")
    public ResponseEntity<?> setAccept(@PathVariable Integer id) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setStatus(2);
            bookingRepository.save(booking);
            if (booking.getUser() != null) {
                Notification notification = notificationService.save("Đơn hàng " + booking.getID().toString() + " đã chấp nhận", booking.getUser());
                messagingTemplate.convertAndSend("/topic/booking/" + booking.getUser().getId().toString(), notification);
            }
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @RolesAllowed("ROLE_RECEPTIONIST")
    @GetMapping("/payment/{id}")
    public ResponseEntity<?> setPayment(@PathVariable Integer id) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            booking.setPayment(1);
            bookingRepository.save(booking);
            if (booking.getUser() != null) {
                Notification notification = notificationService.save("Đơn hàng " + booking.getID().toString() + " đã được thanh toán", booking.getUser());
                messagingTemplate.convertAndSend("/topic/booking/" + booking.getUser().getId().toString(), notification);
            }
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @RolesAllowed("ROLE_RECEPTIONIST")
    @GetMapping("/booking/{phone}")
    public List<BookingHistoryDTO> getBookingForReceptionist(@PathVariable String phone) {
        User receptionist = userReponsitory.findByPhone(phone).get();
        return bookingService.getAllBookings2(receptionist.getBranch().getId());
    }
}
