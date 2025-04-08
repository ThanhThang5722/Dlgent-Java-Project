package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.model.HinhPhong;

import java.util.List;


@Repository
public interface HinhPhongRepo extends JpaRepository<HinhPhong,Long> {
    List<HinhPhong> findByRoomTypeID(Long roomTypeID);

    @Modifying
    @Transactional
    @Query("UPDATE HinhPhong h SET h.isDeleted = 1 WHERE h.id = :id")
    void softDeleteById(@Param("id") Long id);
    List<HinhPhong> findByRoomTypeIDAndIsDeleted(Long roomTypeID, Long isDeleted);
}