package com.example.IS216_Dlegent.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.Phong;

@Repository
public interface PhongRepository extends JpaRepository<Phong, Long> {

    @Query("""
        SELECT p FROM Phong p 
        WHERE NOT EXISTS (
            SELECT 1 FROM ThoiGianPhongBan t
            WHERE t.phong = p
            AND (t.ngayBatDau <= :endTime AND t.ngayKetThuc >= :startTime)
        )
    """)
    List<Phong> findPhongKhongBanTrongKhoang(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );

    @Query("""
        SELECT DISTINCT p FROM Phong p
        JOIN p.loaiPhong lp
        JOIN ThoiGianPhongBan t ON t.phong = p
        JOIN t.khachHang kh
    """)
    List<Phong> findPhongCoLichSuBan();
    
}
