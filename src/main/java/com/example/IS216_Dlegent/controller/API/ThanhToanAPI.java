package com.example.IS216_Dlegent.controller.API;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.HoaDon;
import com.example.IS216_Dlegent.payload.respsonse.HoaDonResponse;
import com.example.IS216_Dlegent.service.DatPhongService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/payment")
public class ThanhToanAPI {

    @Autowired
    private DatPhongService datPhongService;

    @PostMapping("/{datPhongId}")
    public ResponseEntity<?> capNhatTrangThaiVaTaoHoaDon(@PathVariable Long datPhongId) {
        String trangThai = "Đã thanh toán";
        String hinhThucThanhToan = "zalopay";
        List<HoaDon> hoaDons = datPhongService.capNhatTrangThaiVaTaoHoaDon(datPhongId, trangThai, hinhThucThanhToan);
        List<HoaDonResponse> dtos = hoaDons.stream()
                                .map(HoaDonResponse::new)
                                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
