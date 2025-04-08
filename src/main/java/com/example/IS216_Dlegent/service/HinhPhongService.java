package com.example.IS216_Dlegent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.repository.HinhPhongRepo;

@Service
public class HinhPhongService {
    @Autowired
    private HinhPhongRepo hinhPhongRepo;

    public List<HinhPhong> getHinhPhongByRoomTypeID(Long roomTypeID) {
        return hinhPhongRepo.findByRoomTypeID(roomTypeID);
    }
    public HinhPhong save(HinhPhong hinhPhong) {
        return hinhPhongRepo.save(hinhPhong);
    }
}
