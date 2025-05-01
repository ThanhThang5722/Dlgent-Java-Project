package com.example.IS216_Dlegent.repository;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.IS216_Dlegent.model.ServicesOfResort;
import com.example.IS216_Dlegent.payload.dto.DichVuDTO;

import java.util.List;
import java.util.Optional;

public interface ServicesOfResortRepository extends JpaRepository<ServicesOfResort, Long> {
    Optional<ServicesOfResort> findByKhuNghiDuongIdAndDichVuId(Long resortId, Long dichVuId);
    List<ServicesOfResort> findByKhuNghiDuong_IdAndIsDeletedFalse(Long resortId);
    Optional<ServicesOfResort> findByKhuNghiDuong_IdAndDichVu_Id(Long resortId, Long serviceId);
    List<ServicesOfResort> findByKhuNghiDuong_Id(Long resortId);
    
}