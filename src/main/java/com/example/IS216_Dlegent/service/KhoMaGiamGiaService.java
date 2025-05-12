package com.example.IS216_Dlegent.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.model.GiamGia;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.KhoMaGiamGia;
import com.example.IS216_Dlegent.payload.dto.MaGiamGiaDTO;
import com.example.IS216_Dlegent.repository.GiamGiaRepository;
import com.example.IS216_Dlegent.repository.KhachHangRepository;
import com.example.IS216_Dlegent.repository.KhoMaGiamGiaRepository;

@Service
public class KhoMaGiamGiaService {
    @Autowired
    private KhoMaGiamGiaRepository khoMaGiamGiaRepository;

    @Autowired
    private GiamGiaRepository giamGiaRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Transactional
    public void save(Long maGiamGiaId, Long userId) {
        // Lấy đối tượng GiamGia từ repository
        GiamGia giamGia = giamGiaRepository.findById(maGiamGiaId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã giảm giá với ID: " + maGiamGiaId));

        // Lấy đối tượng KhachHang từ repository
        KhachHang khachHang = khachHangRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với ID: " + userId));

        System.out.println("Test GiamGia: " + giamGia);
        System.out.println("Test KhachHang: " + khachHang);

        // Tạo đối tượng KhoMaGiamGia và thiết lập các thuộc tính
        KhoMaGiamGia khoMaGiamGia = new KhoMaGiamGia();
        khoMaGiamGia.setGiamGia(giamGia);
        khoMaGiamGia.setKhachHang(khachHang);

        System.out.println("Test KhoMaGiamGia: " + khoMaGiamGia);

        // Lưu vào database
        khoMaGiamGiaRepository.save(khoMaGiamGia);
    }

    public List<MaGiamGiaDTO> getMaGiamGiaByKhachHangId(Long khachHangId) {

        KhachHang khachHang = khachHangRepository.findById(khachHangId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với ID: " + khachHangId));

        List<KhoMaGiamGia> khoMaGiamGias = khoMaGiamGiaRepository.findByKhachHang(khachHang);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return khoMaGiamGias.stream()
                .map(kho -> {
                    GiamGia giamGia = kho.getGiamGia();
                    return new MaGiamGiaDTO(
                            giamGia.getId(),
                            giamGia.getLoaiGiamGia(),
                            giamGia.getGiaTri(),
                            giamGia.getMucToiDa(),
                            giamGia.getNgayBatDau().format(formatter),
                            giamGia.getNgayKetThuc().format(formatter),
                            giamGia.getDoiDiem().getGiaTri(),
                            "CODE" + giamGia.getId(),
                            "ACTIVE");
                })
                .collect(Collectors.toList());

    }
}
