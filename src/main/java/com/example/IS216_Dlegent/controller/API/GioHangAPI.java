package com.example.IS216_Dlegent.controller.API;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.payload.request.InsertGioHang;
import com.example.IS216_Dlegent.service.ChiTietDatPhongService;
import com.example.IS216_Dlegent.utils.CookieUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class GioHangAPI {
    @Autowired
    private ChiTietDatPhongService chiTietDatPhongService;

    private final Logger logger = LoggerFactory.getLogger(GioHangAPI.class);

    @GetMapping
    public List<ChiTietDatPhongDTO> getCart(HttpServletRequest request) {
        Long khachHangId = CookieUtils.getUserIdFromCookie(request);
        logger.info("Getting cart for user ID: {}", khachHangId);

        return chiTietDatPhongService.getChiTietDatPhongByDatPhongId(khachHangId);
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody InsertGioHang insertGioHang, HttpServletRequest request) {

        Long khachHangId = CookieUtils.getUserIdFromCookie(request);

        if (khachHangId != null) {
            logger.info("Using user ID from cookie: {}", khachHangId);
            insertGioHang.setKhachHangId(khachHangId);
        } else {
            logger.warn("No user ID found in cookie, using ID from request: {}", insertGioHang.getKhachHangId());
        }

        return chiTietDatPhongService.addToCart(insertGioHang);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCart(@RequestParam("id") Long id) {
        return chiTietDatPhongService.deleteChiTietDatPhong(id);
    }
}
