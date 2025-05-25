package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.HoaDon;

public interface HoaDonJPA extends JpaRepository<HoaDon, Long> {
    List<HoaDon> findByDoiTacOrderByThoiGianThanhToanDesc(DoiTac doiTac);
}
