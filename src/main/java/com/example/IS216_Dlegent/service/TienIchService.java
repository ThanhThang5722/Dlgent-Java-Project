package com.example.IS216_Dlegent.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.TienIch;
import com.example.IS216_Dlegent.repository.LoaiPhongRepo;
import com.example.IS216_Dlegent.repository.TienIchRepo;

@Service
public class TienIchService {
    @Autowired
    private TienIchRepo tienIchRepo;
    @Autowired
    private LoaiPhongRepo loaiPhongRepo;
    public List<TienIch> getTienIchByLoaiPhongId(Long loaiPhongId) {
        Optional<LoaiPhong> optional = loaiPhongRepo.findById(loaiPhongId);
        return optional.map(lp -> new ArrayList<>(lp.getTienIchSet()))
                        .orElse(null);
        //return optional.map(LoaiPhong::getTienIchSet).orElse(null);
    }

    @Transactional
    public void addTienIchToLoaiPhong(Long loaiPhongId, Long tienIchId) {
        LoaiPhong loaiPhong = loaiPhongRepo.findById(loaiPhongId).orElseThrow();
        TienIch tienIch = tienIchRepo.findById(tienIchId).orElseThrow();
        loaiPhong.getTienIchSet().add(tienIch);
        loaiPhongRepo.save(loaiPhong);
    }

    @Transactional
    public void removeTienIchFromLoaiPhong(Long loaiPhongId, Long tienIchId) {
        LoaiPhong loaiPhong = loaiPhongRepo.findById(loaiPhongId).orElseThrow();
        TienIch tienIch = tienIchRepo.findById(tienIchId).orElseThrow();
        loaiPhong.getTienIchSet().remove(tienIch);
        loaiPhongRepo.save(loaiPhong);
    }

    public List<TienIch> getAllTienIch() {
        return tienIchRepo.findAll();
    }

    public void updateTienIchPhong(Long loaiPhongId, List<Long> tienIchIds) {
        LoaiPhong loaiPhong = loaiPhongRepo.findById(loaiPhongId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phòng"));

        List<TienIch> tienIchList = tienIchRepo.findAllByIdIn(tienIchIds);
        loaiPhong.setTienIchSet(new HashSet<>(tienIchList));
        loaiPhongRepo.save(loaiPhong);
    }
}
