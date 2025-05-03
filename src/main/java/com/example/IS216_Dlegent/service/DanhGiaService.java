package com.example.IS216_Dlegent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.DanhGia;
import com.example.IS216_Dlegent.payload.dto.DanhGiaDTO;
import com.example.IS216_Dlegent.repository.DanhGiaRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DanhGiaService {

    @Autowired
    private DanhGiaRepository danhGiaRepository;

    public List<DanhGiaDTO> getTop10DanhGiaFormattedByDoiTac(Long doiTacId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return danhGiaRepository
                .findTop10ByDoiTacIdOrderByThoiGianTaoDesc(doiTacId, PageRequest.of(0, 10))
                .stream()
                .map(dg -> {
                    DanhGiaDTO dto = new DanhGiaDTO();
                    dto.setTenKhachHang(dg.getKhachHang().getTaiKhoan().getUsername());
                    dto.setTenResort(dg.getKhuNghiDuong().getTen());
                    dto.setDiem(dg.getDiem());
                    dto.setNoiDung(dg.getNoiDung());
                    dto.setThoiGianTaoFormatted(dg.getThoiGianTao().format(formatter));
                    return dto;
                }).toList();
    }
}