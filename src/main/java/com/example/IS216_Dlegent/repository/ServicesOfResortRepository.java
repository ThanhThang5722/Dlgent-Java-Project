package com.example.IS216_Dlegent.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IS216_Dlegent.model.ServicesOfResort;

import java.util.List;
import java.util.Optional;

public interface ServicesOfResortRepository extends JpaRepository<ServicesOfResort, Long> {
    Optional<ServicesOfResort> findByKhuNghiDuongIdAndDichVuId(Long resortId, Long dichVuId);
    List<ServicesOfResort> findByKhuNghiDuong_IdAndIsDeletedFalse(Long resortId);
    Optional<ServicesOfResort> findByKhuNghiDuong_IdAndDichVu_Id(Long resortId, Long serviceId);
}