package com.example.IS216_Dlegent.controller.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.service.ChiTietDatPhongService;

@RestController
@RequestMapping("/api/cart")
public class GioHangAPI {
    @Autowired
    private ChiTietDatPhongService chiTietDatPhongService;

    @GetMapping
    public List<ChiTietDatPhongDTO> getCart() {
        List<ChiTietDatPhongDTO> chiTietDatPhongDTOs = chiTietDatPhongService.getChiTietDatPhongByDatPhongId();
        
        return chiTietDatPhongDTOs;
    }
}
