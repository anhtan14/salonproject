package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.BookDTO;
import com.example.salonmanage.DTO.CartDTO;
import com.example.salonmanage.DTO.TimeDTO;
import com.example.salonmanage.Entities.*;
import com.example.salonmanage.repository.*;
import com.example.salonmanage.service.BookingDetailService;
import com.example.salonmanage.service.BookingService;
import com.example.salonmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingApi {
    @Autowired
    private BookingDetailService bookingDetailService;
    @Autowired
    private TimesRepository timesRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private userRepository userRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO) {
        User user = userService.findByPhone(cartDTO.getPhone());
        BookingDetail bookingDetail = new BookingDetail();
//        bookingDetail.setBooking();
        List<BookingDetail> listd = bookingDetailRepository.existService(user.getId(), cartDTO.getServiceID());
        Service service = serviceRepository.findServiceById(cartDTO.getServiceID());
        Booking booking = bookingRepository.findBookingByID(2);
        bookingDetail.setBooking(booking);
        bookingDetail.setService(service);
        bookingDetail.setUser(user);
        bookingDetail.setStatus(0);
        if (listd.size() == 0) {
            BookingDetail B = bookingDetailRepository.save(bookingDetail);
            System.out.println("ok ");
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.ok("notok");
        }

    }

    @PostMapping("/listCart")
    public ResponseEntity<?> listCart(@RequestBody CartDTO cartDTO) {
        User user = userService.findByPhone(cartDTO.getPhone());
        List<BookingDetail> list = bookingDetailRepository.findByBookingId(user.getId());
        List<Service> list1 = new ArrayList<>();
        for (BookingDetail a : list
        ) {
            System.out.println(a);
            list1.add(a.getService());
        }
        for (Service b : list1
        ) {
            System.out.println(b);
        }
        return ResponseEntity.ok().body(list1);
    }

    @PostMapping("/deleteDetail")
    public void deleteDetail(@RequestBody CartDTO cartDTO) {
        User user = userService.findByPhone(cartDTO.getPhone());
        BookingDetail b = bookingDetailRepository.exist(user.getId(), cartDTO.getServiceID());
        System.out.println(cartDTO.getServiceID());
        System.out.println(user.getId());
        System.out.println(b);
        b.setStatus(2);
        bookingDetailRepository.save(b);
        System.out.println(b);
//        System.out.println(b1);

    }

    @GetMapping("/listStaff")
    public ResponseEntity<?> listStaff(@RequestParam Integer branch) {

        List<User> list = userRepository.findByBranch(branch);
        List<User> list1 = new ArrayList<>();
        System.out.println(list);
        Role role = roleRepository.findByName("ROLE_CUSTOMER");
        for (User u : list
        ) {
//            Role role1 = roleRepository.findByName(u.getRoles().toString());
////            if(u.getRoles().toString().equals("[ROLE_STAFF]")){
////                list1.add(u);
////            }
            for (Role r : u.getRoles()
            ) {
                if (r.getName().equals("ROLE_STAFF")) {
                    list1.add(u);
                }
            }
        }
        return ResponseEntity.ok().body(list1);
    }

    @PostMapping("/listTime")
    public ResponseEntity<?> listTime(@RequestBody TimeDTO timeDTO) {
        List<Booking> list = bookingRepository.findBookingByDateAndNhanvien(timeDTO.getDate(), timeDTO.getStaffId());
        for (Booking b : list
        ) {
            System.out.println(b.getTimes());
        }
        List<Times> times = timesRepository.findAll();
        for (Booking bo : list
        ) {
            for (Times t : times
            ) {
                if (t.getID() == bo.getTimes().getID()) {
                    times.remove(t);
                    break;

                }
            }
        }

        return ResponseEntity.ok().body(times);
    }
        @PostMapping("/book")
        public ResponseEntity<?> book(@RequestBody BookDTO bookDTO){
            System.out.println(bookDTO);

            User user = userService.findByPhone(bookDTO.getUser());
            List<Booking> listb = bookingRepository.existbooking(user.getId());
            if(listb.size()==0) {
                Booking booking = new Booking();
                booking.setDate(bookDTO.getDate());
                booking.setNhanvien(bookDTO.getNhanvien());
                booking.setUser(user);
                Times times = timesRepository.getById(bookDTO.getTime());
                booking.setTimes(times);
                Branch branch = branchRepository.getById(bookDTO.getBranch());
                booking.setBranch(branch);
                booking.setTotalPrice(bookDTO.getTotalPrice());
                booking.setDiscount(0);
                booking.setStatus(0);
                booking.setPayment(0);
                System.out.println(booking);
                Booking booking1 = bookingRepository.save(booking);
                List<BookingDetail> list = bookingDetailRepository.findByBookingId(user.getId());
                for (BookingDetail b : list
                ) {
                    b.setBooking(booking1);
                    b.setStatus(1);
                    System.out.println(b);
                    bookingDetailRepository.save(b);
                }
                return ResponseEntity.ok("ss");
            }
            else return ResponseEntity.ok("fail");

        }

        @PostMapping("/event")
        public ResponseEntity<?> event() {
            LocalDate date = LocalDate.now();
            Date date1 = java.sql.Date.valueOf(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formatter.format(date1);
            System.out.println(strDate);
            List<event> list = eventRepository.findAll();
            for (event v:list
                 ) {
                if(v.getDate().equals(strDate)){
                    v.setStatus(1);
                    eventRepository.save(v);
                }
            }
            event e = eventRepository.showevent();
            if (e != null) {
                return ResponseEntity.ok().body(e);
            }else {
                return ResponseEntity.ok("not");
            }
        }
    @PostMapping("/discount")
    public ResponseEntity<?> event1(@RequestParam String date) {
        int discount = 0;
        List<event> list = eventRepository.findByDate(date);
        if(list.size()>0) {
            event e = eventRepository.findByDate(date).get(0);

            if (e.getStatus()!=1) {
                discount = e.getDiscount();
            } else {
                discount = 0;
            }
        }
        return ResponseEntity.ok().body(discount);
    }
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelBooking(@RequestParam int bookingid) {
        Booking booking = bookingRepository.findById(bookingid).orElse(null);
        if (booking == null) {
            return ResponseEntity.badRequest().body("Booking not found");
        }
        booking.setStatus(3); // 3 = canceled
        bookingRepository.save(booking);
        return ResponseEntity.ok("ok");
    }
}
