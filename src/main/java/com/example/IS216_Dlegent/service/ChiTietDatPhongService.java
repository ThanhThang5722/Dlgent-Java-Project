package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.GiamGia;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.payload.request.InsertGioHang;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.repository.KhachHangRepository;

@Service
public class ChiTietDatPhongService {
    private Long datPhongId;

    @Autowired
    ChiTietDatPhongRepository chiTietDatPhongRepository;

    @Autowired
    DatPhongRepository datPhongRepository;

    public List<ChiTietDatPhongDTO> getChiTietDatPhongByDatPhongId() {
        List<DatPhong> datPhongs = datPhongRepository.findByKhachHang_Id(1L);

        datPhongId = null;
        for (DatPhong datPhong : datPhongs) {
            if (datPhong.getTinhTrang().equals("Pending")) {
                datPhongId = datPhong.getId();
            }
        }

        if (datPhongId == null) {
            return Collections.emptyList();
        }

        List<ChiTietDatPhongDTO> chiTietDatPhongDTOs = chiTietDatPhongRepository.findByDatPhong_Id(datPhongId)
                .stream()
                .map(ctdp -> new ChiTietDatPhongDTO(
                        ctdp.getId(),
                        ctdp.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong(),
                        ctdp.getSoLuongPhong(),
                        ctdp.getSoLuongDichVuYeuCau(),
                        ctdp.getTongGiaTien().intValue(),
                        ctdp.getNgayBatDau().toString(),
                        ctdp.getNgayKetThuc().toString(),
                        ctdp.getTinhTrang()))
                .collect(Collectors.toList());

        return chiTietDatPhongDTOs;
    }

    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    GoiDatPhongService goiDatPhongService;

    public ResponseEntity<?> addToCart(InsertGioHang insertGioHang) {

        List<DatPhong> datPhongs = datPhongRepository.findByKhachHang_Id(insertGioHang.getKhachHangId());

        DatPhong datPhong = new DatPhong();
        // tim datphong co tinhtrang pending
        for (DatPhong dp : datPhongs) {
            if (dp.getTinhTrang().equals("Pending")) {
                datPhong = dp;
            }
        }
        // neu chua co datphong thi tao moi
        if (datPhong.getId() == null) {
            KhachHang khachHang = khachHangRepository.findById(insertGioHang.getKhachHangId())
                    .orElse(null);

            datPhong.setKhachHang(khachHang);
            datPhong.setTinhTrang("Pending");
            datPhong.setThoiGianTao(LocalDateTime.now());
            datPhong.setTongGiaTien(BigDecimal.ZERO);

            datPhong = datPhongRepository.save(datPhong);
        }
        // LAY THONG TIN GOI DAT PHONG
        GoiDatPhong goiDatPhong = goiDatPhongService.getGoiDatPhongById(insertGioHang.getGoiDatPhongId());
        if (goiDatPhong == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy gói đặt phòng");
        }

        // Tao va luu moi chi tiet dat phong
        ChiTietDatPhong chiTietDatPhong = new ChiTietDatPhong();
        chiTietDatPhong.setDatPhong(datPhong);
        chiTietDatPhong.setGoiDatPhong(goiDatPhong);
        // chiTietDatPhong.setSoLuongPhong(insertGioHang.getSoLuongPhong());
        // chiTietDatPhong.setSoLuongDichVuYeuCau(insertGioHang.getSoLuongDichVu());
        chiTietDatPhong.setSoLuongPhong(0);
        chiTietDatPhong.setSoLuongDichVuYeuCau(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ngayBatDau = LocalDate.parse(insertGioHang.getNgayBatDau(), formatter);
        LocalDate ngayKetThuc = LocalDate.parse(insertGioHang.getNgayKetThuc(), formatter);

        chiTietDatPhong.setNgayBatDau(ngayBatDau.atStartOfDay());
        chiTietDatPhong.setNgayKetThuc(ngayKetThuc.atStartOfDay());

        // Tính tổng tiền, chưa rõ cách tính nên set zero
        chiTietDatPhong.setTongGiaTien(BigDecimal.ZERO);
        chiTietDatPhong.setTinhTrang("Pending");

        chiTietDatPhong = chiTietDatPhongRepository.save(chiTietDatPhong);

        ChiTietDatPhongDTO dto = new ChiTietDatPhongDTO(
                chiTietDatPhong.getId(),
                chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong(),
                chiTietDatPhong.getSoLuongPhong(),
                chiTietDatPhong.getSoLuongDichVuYeuCau(),
                chiTietDatPhong.getTongGiaTien().intValue(),
                chiTietDatPhong.getNgayBatDau().toString(),
                chiTietDatPhong.getNgayKetThuc().toString(),
                chiTietDatPhong.getTinhTrang());

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> deleteChiTietDatPhong(Long id) {
        chiTietDatPhongRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
