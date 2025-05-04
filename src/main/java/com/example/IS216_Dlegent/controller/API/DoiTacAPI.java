package com.example.IS216_Dlegent.controller.API;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.LichSuRutTien;
import com.example.IS216_Dlegent.payload.request.RutTienRequest;
import com.example.IS216_Dlegent.service.DoiTacService;
import com.example.IS216_Dlegent.service.LichSuRutTienService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/partner")
public class DoiTacAPI {
    @Autowired
    private DoiTacService doiTacService;
    @Autowired
    private LichSuRutTienService lichSuRutTien;

    @GetMapping("/sodu")
    public BigDecimal getMethodName(@RequestParam Long doiTacId) {
        return doiTacService.getSoDu(doiTacId);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<?> themYeuCauRutTien(@RequestBody RutTienRequest request) {
        try {
            LichSuRutTien result = lichSuRutTien.themYeuCauRutTien(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
