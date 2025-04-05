package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.LoaiPhong;

@Repository
public interface LoaiPhongRepo extends JpaRepository<LoaiPhong, Long> {
    List<LoaiPhong> findByKhuNghiDuong_IdIn(List<Long> khuNghiDuongIds);
}