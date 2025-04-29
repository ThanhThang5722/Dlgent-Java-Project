package com.example.IS216_Dlegent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ServicesOfResort;
import com.example.IS216_Dlegent.payload.respsonse.ServicesOfResortResponse;
import com.example.IS216_Dlegent.repository.ServicesOfResortRepository;

@Service
public class ServicesOfResortService {

    @Autowired
    private ServicesOfResortRepository repository;

    public ServicesOfResortResponse  getByResortIdAndDichVuId(Long resortId, Long dichVuId) {
        return repository.findByKhuNghiDuongIdAndDichVuId(resortId, dichVuId)
                     .map(this::convertToResponse)
                     .orElse(null);
    }

    private ServicesOfResortResponse convertToResponse(ServicesOfResort entity) {
        return new ServicesOfResortResponse(
                entity.getId(),
                entity.getKhuNghiDuong().getId(),
                entity.getKhuNghiDuong().getTen(),
                entity.getDichVu().getId(),
                entity.getDichVu().getServiceName(),
                entity.getGia(),
                entity.getIsDeleted()
        );
    }
}