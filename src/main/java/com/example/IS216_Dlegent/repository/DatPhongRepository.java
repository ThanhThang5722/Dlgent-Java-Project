package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.DatPhong;

@Repository
public interface DatPhongRepository extends JpaRepository<DatPhong, Long> {
    List<DatPhong> findByKhachHang_Id(Long khachHangId);
}
