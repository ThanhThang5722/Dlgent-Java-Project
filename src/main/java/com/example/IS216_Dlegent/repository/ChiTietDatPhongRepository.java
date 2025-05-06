package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;

@Repository
public interface ChiTietDatPhongRepository extends JpaRepository<ChiTietDatPhong, Long> {
    List<ChiTietDatPhong> findByDatPhong_Id(Long datPhongId);
    
}