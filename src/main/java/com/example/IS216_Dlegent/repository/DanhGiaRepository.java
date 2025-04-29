package com.example.IS216_Dlegent.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.IS216_Dlegent.model.DanhGia;

import java.util.List;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {

    @Query("SELECT dg FROM DanhGia dg " +
           "WHERE dg.khuNghiDuong.doiTac.id = :doiTacId " +
           "ORDER BY dg.thoiGianTao DESC")
    List<DanhGia> findTop10ByDoiTacIdOrderByThoiGianTaoDesc(@Param("doiTacId") Long doiTacId, Pageable pageable);
}
