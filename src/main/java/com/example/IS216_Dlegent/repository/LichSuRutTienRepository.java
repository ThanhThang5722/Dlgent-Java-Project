package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IS216_Dlegent.model.LichSuRutTien;

import java.util.List;

public interface LichSuRutTienRepository extends JpaRepository<LichSuRutTien, Long> {
    List<LichSuRutTien> findByDoiTacIdOrderByThoiGianTaoDesc(Long doiTacId); // Tùy bạn muốn lọc theo đối tác hay không
    List<LichSuRutTien> findAllByOrderByThoiGianTaoDesc();
}