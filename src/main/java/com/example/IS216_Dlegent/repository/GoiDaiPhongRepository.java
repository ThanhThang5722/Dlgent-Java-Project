package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IS216_Dlegent.model.GoiDatPhong;

public interface GoiDaiPhongRepository extends JpaRepository<GoiDatPhong, Long>{

    public List<GoiDatPhong> findByLoaiPhong_Id(Long loaiPhongId);
}
