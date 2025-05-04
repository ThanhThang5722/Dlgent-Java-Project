package com.example.IS216_Dlegent.controller.API;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.LichSuRutTien;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.LichSuRutTienRepository;
import com.example.IS216_Dlegent.service.DoiTacService;

@RestController
@RequestMapping("/api/admin")
public class AdminAPI {
    @Autowired
    private LichSuRutTienRepository lichSuRutTienRepository;
    @Autowired
    private DoiTacService doiTacService;

    @Autowired
    private DoiTacRepository doiTacRepository;

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<?> duyetRutTien(@PathVariable Long id) {
        Optional<LichSuRutTien> optional = lichSuRutTienRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LichSuRutTien yeuCau = optional.get();
        if (!"Chờ phê duyệt".equals(yeuCau.getTrangThaiRutTien())) {
            return ResponseEntity.badRequest().body("Yêu cầu đã được xử lý.");
        }

        DoiTac doiTac = doiTacRepository.findById(yeuCau.getDoiTacId())
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy đối tác"));

        BigDecimal soTienRut = yeuCau.getSoTien();
        BigDecimal soDuHienTai = doiTac.getSoDu();

        if (soDuHienTai.compareTo(soTienRut) < 0) {
            return ResponseEntity.badRequest().body("Không đủ số dư để duyệt yêu cầu.");
        }

        // Trừ tiền
        doiTac.setSoDu(soDuHienTai.subtract(soTienRut));
        doiTacRepository.save(doiTac);

        // Cập nhật trạng thái và thời gian duyệt
        yeuCau.setTrangThaiRutTien("Đã duyệt");
        yeuCau.setThoiGianRutTien(LocalDateTime.now());
        lichSuRutTienRepository.save(yeuCau);

        return ResponseEntity.ok(doiTac.getSoDu());
    }
}
