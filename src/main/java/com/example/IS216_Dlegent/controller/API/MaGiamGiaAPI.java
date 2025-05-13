package com.example.IS216_Dlegent.controller.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.MaGiamGiaDTO;
import com.example.IS216_Dlegent.payload.request.QuyDoiRequest;
import com.example.IS216_Dlegent.service.DiemService;
import com.example.IS216_Dlegent.service.MaGiamGiaService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class MaGiamGiaAPI {
    @Autowired
    private DiemService diemService;

    @Autowired
    private MaGiamGiaService maGiamGiaService;

    @GetMapping("/diem")
    public Integer getDiem(@RequestParam Long userId) {
        return diemService.getDiemByUserId(userId);
    }

    @GetMapping("/danh-sach")
    public List<MaGiamGiaDTO> getDanhSachMaGiam() {
        return maGiamGiaService.getDanhSach();
    }

    @PutMapping("/quy-doi/{id}")
    public ResponseEntity<?> quyDoiDiem(@PathVariable Long id, @RequestBody QuyDoiRequest quyDoiRequest) {
        return maGiamGiaService.quyDoiDiem(id, quyDoiRequest.getUserId());
    }
}
