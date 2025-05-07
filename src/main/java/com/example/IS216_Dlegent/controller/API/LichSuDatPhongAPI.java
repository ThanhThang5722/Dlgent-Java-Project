package com.example.IS216_Dlegent.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.BookingListDTO;
import com.example.IS216_Dlegent.service.BookingListService;

@RestController
@RequestMapping("/api/booking-history")
public class LichSuDatPhongAPI {
    @Autowired
    private BookingListService bookingListService;
    
    @GetMapping
    public BookingListDTO getBookingHistory(@RequestParam Long khachHangId) {
        return bookingListService.getBookingHistory(khachHangId);
    }   
}
