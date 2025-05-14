package com.example.salonmanage.service;

import com.example.salonmanage.Entities.BookingDetail;
import com.example.salonmanage.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BookingDetailServiceImpl implements BookingDetailService{
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void save(BookingDetail bookingDetail) {

    }
}
