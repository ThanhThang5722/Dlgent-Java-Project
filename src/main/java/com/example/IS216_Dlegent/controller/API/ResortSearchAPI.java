package com.example.IS216_Dlegent.controller.API;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.respsonse.ResortSearchResponse;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;

@RestController
@RequestMapping("/api/resorts")
public class ResortSearchAPI {

    @Autowired
    private KhuNghiDuongService khuNghiDuongService;

    @GetMapping("/search")
    public ResponseEntity<List<ResortSearchResponse>> searchResorts(
            @RequestParam(required = false) String searchTerm,
            @RequestParam String checkIn, // yyyy-MM-dd'T'HH:mm:ss
            @RequestParam String checkOut,
            @RequestParam(defaultValue = "2") int soNguoi) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime ngayNhan = LocalDateTime.parse(checkIn, formatter);
        LocalDateTime ngayTra = LocalDateTime.parse(checkOut, formatter);

        List<ResortSearchResponse> ketQuaTimKiem = khuNghiDuongService.searchResorts(searchTerm, ngayNhan, ngayTra,
                soNguoi);

        return ResponseEntity.ok(ketQuaTimKiem);
    }
}