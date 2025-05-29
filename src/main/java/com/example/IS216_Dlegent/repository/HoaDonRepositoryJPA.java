package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IS216_Dlegent.model.HoaDon;

public interface HoaDonRepositoryJPA extends JpaRepository<HoaDon, Long>{
    HoaDon findByChiTietDatPhong_Id(Long chiTietDatPhongId);
}
