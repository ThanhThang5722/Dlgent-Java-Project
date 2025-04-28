package com.example.IS216_Dlegent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.repository.GoiDaiPhongRepository;

@Service
public class GoiDatPhongService {
    @Autowired
    GoiDaiPhongRepository goiDaiPhongRepository;
    public List<GoiDatPhong> getListGoiDatPhongByRoomTypeId(Long loaiPhongId) {
        return goiDaiPhongRepository.findByLoaiPhong_Id(loaiPhongId);
    }

    public GoiDatPhong insertGoiDatPhong(GoiDatPhong goiDatPhong) {
        return goiDaiPhongRepository.save(goiDatPhong);
    }
    
    public GoiDatPhong getGoiDatPhongById(Long id) {
        return goiDaiPhongRepository.findById(id).orElse(null);
    }
    
    public void deleteGoiDatPhong(Long id) {
        goiDaiPhongRepository.deleteById(id);
    }
}
