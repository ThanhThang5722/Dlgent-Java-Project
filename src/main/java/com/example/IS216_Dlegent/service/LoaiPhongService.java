package com.example.IS216_Dlegent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.repository.LoaiPhongRepo;

@Service
public class LoaiPhongService {

    private final LoaiPhongRepo loaiPhongRepository;
    @Autowired
    private final KhuNghiDuongService khuNghiDuongService;

    public LoaiPhongService(LoaiPhongRepo loaiPhongRepository, KhuNghiDuongService khuNghiDuongService) {
        this.loaiPhongRepository = loaiPhongRepository;
        this.khuNghiDuongService = khuNghiDuongService;
    }

    public List<LoaiPhong> getRoomTypesByPartnerId(Long doiTacId) {
        List<KhuNghiDuong> khuNghiDuongList = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        List<Long> khuNghiDuongIds = khuNghiDuongList.stream().map(KhuNghiDuong::getId).toList();
        return loaiPhongRepository.findByKhuNghiDuong_IdIn(khuNghiDuongIds);
    }


}