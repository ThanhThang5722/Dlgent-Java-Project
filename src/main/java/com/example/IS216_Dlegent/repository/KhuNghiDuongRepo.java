package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.model.KhuNghiDuong;

@Repository
public interface KhuNghiDuongRepo extends JpaRepository<KhuNghiDuong, Long> {
    List<KhuNghiDuong> findByDoiTac_Id(Long doiTacId);

    @Modifying
    @Transactional
    @Query("UPDATE KhuNghiDuong k SET k.ten = :ten, k.diaChi = :diaChi WHERE k.id = :id")
    int updateTenAndDiaChiById(@Param("id") Long id, @Param("ten") String ten, @Param("diaChi") String diaChi);
}