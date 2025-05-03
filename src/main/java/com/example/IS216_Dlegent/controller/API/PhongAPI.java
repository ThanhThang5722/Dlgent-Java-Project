package com.example.IS216_Dlegent.controller.API;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.Phong;
import com.example.IS216_Dlegent.model.ThoiGianPhongBan;
import com.example.IS216_Dlegent.payload.dto.PhongDTO;
import com.example.IS216_Dlegent.payload.dto.ThoiGianPhongBanDTO;
import com.example.IS216_Dlegent.payload.dto.ThoiGianYeuCauDTO;
import com.example.IS216_Dlegent.payload.request.RoomRequest;
import com.example.IS216_Dlegent.payload.request.UpdatePhongRequest;
import com.example.IS216_Dlegent.service.PhongService;
import com.example.IS216_Dlegent.service.ThoiGianPhongBanService;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/resort/phong")
public class PhongAPI {
    @Autowired
    private ThoiGianPhongBanService thoiGianPhongBanService;
    @Autowired
    private PhongService phongService;

    public PhongAPI(ThoiGianPhongBanService thoiGianPhongBanService, PhongService phongService) {
        this.thoiGianPhongBanService = thoiGianPhongBanService;
        this.phongService = phongService;
    }
    @PostMapping("/kiem-tra")
    public ResponseEntity<Map<String, Object>> kiemTraPhong(@RequestBody ThoiGianYeuCauDTO thoiGianYeuCau) {
        LocalDateTime batDau = thoiGianYeuCau.getNgayBatDau();
        LocalDateTime ketThuc = thoiGianYeuCau.getNgayKetThuc();

        List<ThoiGianPhongBan> phongBan = thoiGianPhongBanService
            .getLichSuPhongBanTrongKhoangThoiGian(batDau, ketThuc);

        List<Phong> phongKhongBan = phongService
            .getPhongKhongBanTrongKhoangThoiGian(batDau, ketThuc);

        // Convert entities to DTOs
        List<ThoiGianPhongBanDTO> phongBanDTOs = phongBan.stream()
                .map(tg -> new ThoiGianPhongBanDTO().convertToDTO(tg)).collect(Collectors.toList());

        List<PhongDTO> phongKhongBanDTOs = phongKhongBan.stream()
                .map(p -> new PhongDTO().convertToDTO(p)).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("phongBan", phongBanDTOs);
        response.put("phongKhongBan", phongKhongBanDTOs);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> postMethodName(@RequestBody RoomRequest entity) {
        phongService.createPhong(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }
    

    @PutMapping("")
    public ResponseEntity<?> updateTinhTrangPhong(@RequestBody UpdatePhongRequest entity) {
        //TODO: process PUT request
        phongService.updateTinhTrangPhong(entity.getPhongId(), entity.getTinhTrang());
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> xoaPhong(@RequestBody Long idPhong) {
        if(phongService.xoaPhongTheoId(idPhong)) {
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
}
