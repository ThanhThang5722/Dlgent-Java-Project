package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.GoiDatPhong;

import java.util.List;

@Repository
public interface DichVuMacDinhRepository extends JpaRepository<DichVuMacDinh, Long> {
    List<DichVuMacDinh> findByGoiDatPhong(GoiDatPhong goiDatPhong);
    List<DichVuMacDinh> findByGoiDatPhong_Id(Long goiDatPhongId);
} 