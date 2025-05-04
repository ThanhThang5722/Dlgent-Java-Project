package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;

public interface ChiTietDatPhongRepository extends JpaRepository<ChiTietDatPhong, Long>{
    List<ChiTietDatPhong> findByDatPhong_Id(Long datPhongId);
}