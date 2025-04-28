package com.example.IS216_Dlegent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.repository.DichVuMacDinhRepository;

@Service
public class DichVuMacDinhService {
    
    @Autowired
    private DichVuMacDinhRepository dichVuMacDinhRepository;
    
    public DichVuMacDinh save(DichVuMacDinh dichVuMacDinh) {
        return dichVuMacDinhRepository.save(dichVuMacDinh);
    }
    
    public List<DichVuMacDinh> saveAll(List<DichVuMacDinh> dichVuMacDinhs) {
        return dichVuMacDinhRepository.saveAll(dichVuMacDinhs);
    }
    
    public List<DichVuMacDinh> findByGoiDatPhong(GoiDatPhong goiDatPhong) {
        return dichVuMacDinhRepository.findByGoiDatPhong(goiDatPhong);
    }
    
    public List<DichVuMacDinh> findByGoiDatPhongId(Long goiDatPhongId) {
        return dichVuMacDinhRepository.findByGoiDatPhong_Id(goiDatPhongId);
    }
    
    public void delete(Long id) {
        dichVuMacDinhRepository.deleteById(id);
    }
    
    public void deleteByGoiDatPhongId(Long goiDatPhongId) {
        List<DichVuMacDinh> dichVuMacDinhs = findByGoiDatPhongId(goiDatPhongId);
        dichVuMacDinhRepository.deleteAll(dichVuMacDinhs);
    }
} 