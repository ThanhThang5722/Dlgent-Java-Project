package com.example.IS216_Dlegent.controller.API;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.payload.request.InsertResortRequest;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;
import com.example.IS216_Dlegent.service.ResortService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/resort")
public class KhuNghiDuongAPI {
    @Autowired
    private final KhuNghiDuongService khuNghiDuongService;
    @Autowired
    public KhuNghiDuongAPI(KhuNghiDuongService khuNghiDuongService) {
        this.khuNghiDuongService = khuNghiDuongService;
    }
    
    @PostMapping("/insert")
    public ResponseEntity<?> insertResort(@RequestBody InsertResortRequest resortRequest, HttpServletRequest request) {
        String authToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }

        /*boolean success = khuNghiDuongService.insertResort(
                authToken,
                resortRequest.getResortName(),
                resortRequest.getAddress(),
                resortRequest.getCity(),
                resortRequest.getDistrict(),
                resortRequest.getProvince()
        );*/
        boolean success = khuNghiDuongService.insertResort(authToken, resortRequest);

        if (success) {
            return ResponseEntity.ok(Map.of("message", "Resort successfully added!"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add resort."));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<KhuNghiDuong> updateKhuNghiDuong(@PathVariable Long id, @RequestBody KhuNghiDuong updatedKhuNghiDuong) {
        KhuNghiDuong khuNghiDuong = khuNghiDuongService.findById(id);
        if (khuNghiDuong == null) {
            return ResponseEntity.notFound().build();
        }

        khuNghiDuong.setTen(updatedKhuNghiDuong.getTen());
        khuNghiDuong.setDiaChi(updatedKhuNghiDuong.getDiaChi());
        khuNghiDuong.setDanhGia(updatedKhuNghiDuong.getDanhGia());
        // Cập nhật các trường khác như city, district, province...

        khuNghiDuongService.save(khuNghiDuong);
        return ResponseEntity.ok(khuNghiDuong);
    }
}
