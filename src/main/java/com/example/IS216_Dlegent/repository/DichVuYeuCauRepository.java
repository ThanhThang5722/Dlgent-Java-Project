package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.DichVuYeuCau;

@Repository
public interface DichVuYeuCauRepository extends JpaRepository<DichVuYeuCau, Long> {
    List<DichVuYeuCau> findByChiTietDatPhong_Id(Long chiTietDatPhongId);
}
