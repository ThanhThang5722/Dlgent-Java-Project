package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.KhoMaGiamGia;

@Repository
public interface KhoMaGiamGiaRepository extends JpaRepository<KhoMaGiamGia, Long> {
    List<KhoMaGiamGia> findByKhachHang(KhachHang khachHang);
}
