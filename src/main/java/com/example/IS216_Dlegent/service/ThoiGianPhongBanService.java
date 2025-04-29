package com.example.IS216_Dlegent.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ThoiGianPhongBan;
import com.example.IS216_Dlegent.repository.ThoiGianPhongBanRepository;

@Service
public class ThoiGianPhongBanService {
    @Autowired
    private ThoiGianPhongBanRepository thoiGianPhongBanRepository;
    
    public ThoiGianPhongBanService(ThoiGianPhongBanRepository repository) {
        this.thoiGianPhongBanRepository = repository;
    }

    List<ThoiGianPhongBan> getLichSuPhongBan() {
        return thoiGianPhongBanRepository.findLichSuPhongBan();
    }

    public List<ThoiGianPhongBan> getLichSuPhongBanTrongKhoangThoiGian(LocalDateTime batDau, LocalDateTime ketThuc) {
    return thoiGianPhongBanRepository.findAll().stream()
        .filter(tg -> tg.getNgayKetThuc().isAfter(batDau) && tg.getNgayBatDau().isBefore(ketThuc))
        .collect(Collectors.toList());
    }
}
