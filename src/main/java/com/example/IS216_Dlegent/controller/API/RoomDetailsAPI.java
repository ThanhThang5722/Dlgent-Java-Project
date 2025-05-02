package com.example.IS216_Dlegent.controller.API;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.RoomTypeDTO;
import com.example.IS216_Dlegent.service.LoaiPhongService;

@RestController
@RequestMapping("/api/resorts/{id}")
public class RoomDetailsAPI {

    @Autowired
    private LoaiPhongService loaiPhongService;

    @GetMapping("/roomtypes")
    public ResponseEntity<List<RoomTypeDTO>> searchRooms(
            @PathVariable("id") Long resortId,
            @RequestParam String checkIn, // yyyy-MM-dd'T'HH:mm:ss
            @RequestParam String checkOut,
            @RequestParam(defaultValue = "2") int soNguoi) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime ngayNhan = LocalDateTime.parse(checkIn, formatter);
        LocalDateTime ngayTra = LocalDateTime.parse(checkOut, formatter);

        List<RoomTypeDTO> result = loaiPhongService.getRoomByResort(resortId, ngayNhan, ngayTra,
                soNguoi);

        return ResponseEntity.ok(result);
    }
}
