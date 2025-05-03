package com.example.IS216_Dlegent.controller.API;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.DanhGia;
import com.example.IS216_Dlegent.payload.dto.DanhGiaDTO;
import com.example.IS216_Dlegent.payload.request.InsertDanhGiaRequest;
import com.example.IS216_Dlegent.service.DanhGiaService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/danh-gia")
public class DanhGiaAPI {

    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping("/resort/{resortId}")
    public ResponseEntity<Page<DanhGiaDTO>> getDanhGias(
            @PathVariable Long resortId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<DanhGiaDTO> response = danhGiaService.getDanhGiaDTOsByResortId(resortId, page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> insertDanhGia(
            @RequestParam("resortId") Long resortId,
            @RequestParam("khachHangId") Long khachHangId,
            @RequestParam("diem") int diem,
            @RequestParam("noiDung") String noiDung,
            HttpServletRequest request) {
        try {
            InsertDanhGiaRequest dto = new InsertDanhGiaRequest();
            dto.setResortId(resortId);
            dto.setCustomerId(khachHangId);
            dto.setDiem(diem);
            dto.setNoiDung(noiDung);

            danhGiaService.insertDanhGia(dto);
            return ResponseEntity.ok("Đánh giá đã được ghi nhận.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
