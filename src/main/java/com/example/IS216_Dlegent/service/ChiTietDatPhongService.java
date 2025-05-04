package com.example.IS216_Dlegent.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;

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
}
