package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.ThoiGianPhongBan;

@Repository
public interface ThoiGianPhongBanRepository extends JpaRepository<ThoiGianPhongBan, Long> {

    @Query("""
        SELECT t FROM ThoiGianPhongBan t
        JOIN FETCH t.phong p
        JOIN FETCH p.loaiPhong
        JOIN FETCH t.khachHang
        ORDER BY t.ngayBatDau DESC
    """)
    public List<ThoiGianPhongBan> findLichSuPhongBan();
    
}
