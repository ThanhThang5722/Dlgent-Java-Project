package com.example.IS216_Dlegent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.repository.LoaiPhongRepo;

@Service
public class LoaiPhongService {

    private final LoaiPhongRepo loaiPhongRepository;

    public LoaiPhongService(LoaiPhongRepo loaiPhongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
    }

    public List<LoaiPhong> getRoomTypeByPartnerID(Long ID) {
        return loaiPhongRepository.findByDoiTacId(ID);
    }
}
