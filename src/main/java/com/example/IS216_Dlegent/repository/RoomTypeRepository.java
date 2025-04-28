package com.example.IS216_Dlegent.repository;

import java.util.List;

import com.example.IS216_Dlegent.payload.SSR.RoomTypeDetailsDTO;

public interface RoomTypeRepository {
    RoomTypeDetailsDTO findById(Long id);
    List<RoomTypeDetailsDTO> findAll();
    List<RoomTypeDetailsDTO> findAllByPartnerID(Long partnerId);
    void save(RoomTypeDetailsDTO roomTypeDetailsDTO);
    void delete(Long id);
}
