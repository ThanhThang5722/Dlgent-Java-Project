package com.example.IS216_Dlegent.controller.API;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.request.InsertResortRequest;
import com.example.IS216_Dlegent.payload.request.KhuNghiDuongRequest;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;
import com.example.IS216_Dlegent.service.LoaiPhongService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
        boolean success = khuNghiDuongService.insertResort(authToken, resortRequest);

        if (success) {
            return ResponseEntity.ok(Map.of("message", "Resort successfully added!"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add resort."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhuNghiDuongRequest> updateKhuNghiDuong(@PathVariable Long id, @RequestBody KhuNghiDuongRequest updatedKhuNghiDuong) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Ten Resort Moi: {}", updatedKhuNghiDuong.getTen());
        logger.info("Dia Chi Moi: {}", updatedKhuNghiDuong.getDiaChi());
        if(!khuNghiDuongService.updateKhuNghiDuong(id, updatedKhuNghiDuong.getTen(), updatedKhuNghiDuong.getDiaChi())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedKhuNghiDuong);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteResorts(@RequestBody List<Long> ids) {
        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Lỗi danh sách ID không được rỗng"));
        }

        if(!khuNghiDuongService.hardDeleteKhuNghiDuong(ids)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Xóa thất bại"));
        }
        return ResponseEntity.ok(Map.of("message", "Xóa thành công!"));
    }
}
