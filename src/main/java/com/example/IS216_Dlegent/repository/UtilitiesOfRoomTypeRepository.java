package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.UtilitiesOfRoomType;

@Repository
public interface UtilitiesOfRoomTypeRepository extends JpaRepository<UtilitiesOfRoomType, Long> {
    List<UtilitiesOfRoomType> findByLoaiPhong_Id(Long id);
}
