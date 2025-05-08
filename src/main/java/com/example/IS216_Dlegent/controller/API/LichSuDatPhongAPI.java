package com.example.IS216_Dlegent.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.BookingListDTO;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO2;
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

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingDetail(@PathVariable Long bookingId) {
        return bookingListService.getBookingDetail(bookingId);
    }
}
