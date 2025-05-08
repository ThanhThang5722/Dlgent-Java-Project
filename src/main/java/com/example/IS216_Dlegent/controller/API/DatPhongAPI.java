package com.example.IS216_Dlegent.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO2;
import com.example.IS216_Dlegent.service.DatPhongService;

@RestController
@RequestMapping("/api/booking")
public class DatPhongAPI {
    @Autowired
    DatPhongService datPhongService;
    
    @PutMapping("/{bookingId}")
    public ResponseEntity<?> huyPhong(@PathVariable Long bookingId) {
        return datPhongService.huyPhong(bookingId);
    }
}
