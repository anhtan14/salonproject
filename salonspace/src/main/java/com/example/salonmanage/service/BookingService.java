package com.example.salonmanage.service;

import com.example.salonmanage.DTO.*;
import com.example.salonmanage.Entities.Booking;
import com.example.salonmanage.Entities.User;
import com.example.salonmanage.repository.BookingRepository;
import com.example.salonmanage.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    private userRepository userRepository;

    public List<BookingHistoryDTO> getUserBookingHistoryByPhone(String phone) {
        User u = userRepository.getByPhone(phone);
        List<Booking> bookings = bookingRepository.findByUserPhone(u.getId());
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BookingHistoryDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BookingHistoryDTO> getAllBookings2(int branch) {
        List<Booking> bookings = bookingRepository.findAllWithNotRemoveWithBranch(branch);
        return bookings.stream().map(this::convertToDTO2).collect(Collectors.toList());
    }

    private BookingHistoryDTO convertToDTO(Booking booking) {
        BookingHistoryDTO bookingHistoryDTO = new BookingHistoryDTO();
        bookingHistoryDTO.setId(booking.getID());
        bookingHistoryDTO.setDate(booking.getDate());
        bookingHistoryDTO.setDiscount(booking.getDiscount());
        bookingHistoryDTO.setStatus(booking.getStatus());
        bookingHistoryDTO.setPayment(booking.getPayment());
        bookingHistoryDTO.setTotalPrice(booking.getTotalPrice());
        if (booking.getNhanvien() != null) {
            bookingHistoryDTO.setNhanvienName(userRepository.findById(booking.getNhanvien()).get().getName());
        }
        // Convert User entity to UserDTO

        if (booking.getUser() != null) {
            userDTO userDTO = new userDTO();
            userDTO.setId(booking.getUser().getId());
            userDTO.setPhone(booking.getUser().getPhone());

            bookingHistoryDTO.setUser(userDTO);
        }
        // Convert Branch entity to BranchDTO
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setId(booking.getBranch().getId());
        branchDTO.setAddress(booking.getBranch().getAddress());


        bookingHistoryDTO.setBranch(branchDTO);

        SetTime setTime = new SetTime();
        if (booking.getTimes() != null) {
            setTime.setTimes(booking.getTimes().getTime());
            bookingHistoryDTO.setTime(setTime);
        }
        return bookingHistoryDTO;
    }

    private BookingHistoryDTO convertToDTO2(Booking booking) {
        BookingHistoryDTO bookingHistoryDTO = new BookingHistoryDTO();
        bookingHistoryDTO.setId(booking.getID());
        bookingHistoryDTO.setDate(booking.getDate());
        bookingHistoryDTO.setDiscount(booking.getDiscount());
        bookingHistoryDTO.setStatus(booking.getStatus());
        bookingHistoryDTO.setPayment(booking.getPayment());
        bookingHistoryDTO.setTotalPrice(booking.getTotalPrice());
        if (booking.getNhanvien() != null) {
            bookingHistoryDTO.setNhanvienName(userRepository.findById(booking.getNhanvien()).get().getName());
        }
        // Convert User entity to UserDTO

        if (booking.getUser() != null) {
            userDTO userDTO = new userDTO();
            userDTO.setId(booking.getUser().getId());
            userDTO.setPhone(booking.getUser().getName());

            bookingHistoryDTO.setUser(userDTO);
        }
        // Convert Branch entity to BranchDTO
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setId(booking.getBranch().getId());
        branchDTO.setAddress(booking.getBranch().getAddress());

        bookingHistoryDTO.setBranch(branchDTO);

        SetTime setTime = new SetTime();
        if (booking.getTimes() != null) {
            setTime.setTimes(booking.getTimes().getTime());
            bookingHistoryDTO.setTime(setTime);
        }
        return bookingHistoryDTO;
    }
}
