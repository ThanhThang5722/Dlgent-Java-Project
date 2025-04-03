package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.KhuNghiDuong;

@Repository
public interface KhuNghiDuongRepo extends JpaRepository<KhuNghiDuong, Long> {
    //List<KhuNghiDuong> findByThanhPhoAndQuanAndPhuong(String thanhPho, String quan, String phuong);
    List<KhuNghiDuong> findByDoiTac_Id(Long doiTacId);
}
