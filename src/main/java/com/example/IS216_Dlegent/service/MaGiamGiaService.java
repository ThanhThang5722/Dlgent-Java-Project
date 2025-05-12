package com.example.IS216_Dlegent.service;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.GiamGia;
import com.example.IS216_Dlegent.model.KhoMaGiamGia;
import com.example.IS216_Dlegent.payload.dto.MaGiamGiaDTO;
import com.example.IS216_Dlegent.repository.DoiDiemRepository;
import com.example.IS216_Dlegent.repository.GiamGiaRepository;

@Service
public class MaGiamGiaService {
    @Autowired
    private GiamGiaRepository giamGiaRepository;

    @Autowired
    private DiemService diemService;

    @Autowired
    private KhoMaGiamGiaService khoMaGiamGiaService;

    public List<MaGiamGiaDTO> getDanhSach() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return giamGiaRepository.findAll().stream()
                .filter(gg -> gg.getNgayKetThuc().isAfter(LocalDateTime.now()))
                .map(gg -> new MaGiamGiaDTO(
                        gg.getId(),
                        gg.getLoaiGiamGia(),
                        gg.getGiaTri(),
                        gg.getMucToiDa(),
                        gg.getNgayBatDau().format(formatter),
                        gg.getNgayKetThuc().format(formatter),
                        gg.getDoiDiem().getGiaTri(),
                        "CODE" + gg.getId(),
                        "ACTIVE"))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> quyDoiDiem(Long id, Long userId) {
        GiamGia giamGia = giamGiaRepository.findById(id).orElse(null);
        if (giamGia == null) {
            return ResponseEntity.badRequest().body("Mã giảm giá không tồn tại");
        }

        if (giamGia.getNgayKetThuc().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Mã giảm giá đã hết hạn");
        }

        if (giamGia.getDoiDiem().getGiaTri() > diemService.getDiemByUserId(userId)) {
            return ResponseEntity.badRequest().body("Số điểm không đủ");
        }

        diemService.updateDiem(userId, -giamGia.getDoiDiem().getGiaTri());

        khoMaGiamGiaService.save(id, userId);

        return ResponseEntity.ok().body("Quy đổi thành công");
    }
}
