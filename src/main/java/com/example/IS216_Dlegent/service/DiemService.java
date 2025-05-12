package com.example.IS216_Dlegent.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.repository.DoiDiemRepository;
import com.example.IS216_Dlegent.repository.KhachHangRepository;

@Service
public class DiemService {
    @Autowired
    KhachHangRepository khachHangRepository;

    public Integer getDiemByUserId(Long userId) {
        Optional<KhachHang> khachHangOtp = khachHangRepository.findById(userId);

        if (!khachHangOtp.isPresent()) {
            return 0;
        }

        KhachHang khachHang = khachHangOtp.get();

        return khachHang.getDiemTichLuy();
    }

    public void updateDiem(Long userId, Integer soDiem) {
        KhachHang khachHang = khachHangRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
        khachHang.setDiemTichLuy(khachHang.getDiemTichLuy() + soDiem);
        khachHangRepository.save(khachHang);
    }

}
