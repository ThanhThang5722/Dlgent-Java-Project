package com.example.IS216_Dlegent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.HinhKhuNghiDuong;
import com.example.IS216_Dlegent.repository.HinhKhuNghiDuongRepo;

@Service
public class HinhKhuNghiDuongService {
    @Autowired
    private HinhKhuNghiDuongRepo hinhKhuNghiDuongRepo;

    public List<HinhKhuNghiDuong> getHinhKhuNghiDuongByResortId(Long resortId) {
        return hinhKhuNghiDuongRepo.findAllByIdKhuNghiDuong_Id(resortId);
    }
}
