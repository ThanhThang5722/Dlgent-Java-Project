package com.example.IS216_Dlegent.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.Phong;
import com.example.IS216_Dlegent.model.ThoiGianPhongBan;
import com.example.IS216_Dlegent.payload.request.RoomRequest;
import com.example.IS216_Dlegent.repository.KhuNghiDuongRepo;
import com.example.IS216_Dlegent.repository.LoaiPhongRepo;
import com.example.IS216_Dlegent.repository.PhongRepository;

@Service
public class PhongService {

    @Autowired
    private PhongRepository phongRepository;
    @Autowired
    private ThoiGianPhongBanService thoiGianPhongBanService;
    @Autowired
    private KhuNghiDuongRepo khuNghiDuongRepository;
    @Autowired
    private LoaiPhongRepo loaiPhongRepository;

    public List<Phong> getPhongKhongBan(LocalDateTime start, LocalDateTime end) {
        return phongRepository.findPhongKhongBanTrongKhoang(start, end);
    }

    public List<Phong> getPhongBan() {
        return phongRepository.findPhongCoLichSuBan();
    }
    public List<Phong> getPhongKhongBanTrongKhoangThoiGian(LocalDateTime batDau, LocalDateTime ketThuc) {
        List<Phong> allPhongs = phongRepository.findAll();
        List<ThoiGianPhongBan> phongBan = thoiGianPhongBanService.getLichSuPhongBanTrongKhoangThoiGian(batDau, ketThuc);

        Set<Long> phongBanIds = phongBan.stream()
            .map(tg -> tg.getPhong().getId())
            .collect(Collectors.toSet());

        return allPhongs.stream()
            .filter(phong -> !phongBanIds.contains(phong.getId()))
            .collect(Collectors.toList());
    }

    public Phong createPhong(RoomRequest dto) {
        KhuNghiDuong khuNghiDuong = khuNghiDuongRepository.findById(dto.getKhuNghiDuongId())
            .orElseThrow(() -> new RuntimeException("Khu nghỉ dưỡng không tồn tại"));

        LoaiPhong loaiPhong = loaiPhongRepository.findById(dto.getLoaiPhongId())
            .orElseThrow(() -> new RuntimeException("Loại phòng không tồn tại"));

        Phong phong = new Phong();
        phong.setKhuNghiDuong(khuNghiDuong);
        phong.setLoaiPhong(loaiPhong);
        phong.setMaSo(dto.getMaSo());
        phong.setTinhTrang(dto.getTinhTrang());

        return phongRepository.save(phong);
    }
    public Phong updateTinhTrangPhong(Long phongId, String tinhTrang) {
        Phong phong = phongRepository.findById(phongId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng với ID: " + phongId));

        phong.setTinhTrang(tinhTrang);
        return phongRepository.save(phong);
    }

    public boolean xoaPhongTheoId(Long phongId) {
        if(phongRepository.existsById(phongId)) {
            phongRepository.deleteById(phongId);
            return true;
        }
        return false;
    }
}