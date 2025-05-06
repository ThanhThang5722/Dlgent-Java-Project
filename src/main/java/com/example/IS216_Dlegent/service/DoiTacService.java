package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.repository.DoiTacRepository;

@Service
public class DoiTacService {
    @Autowired
    private DoiTacRepository doiTacRepository;
    public BigDecimal getSoDu(Long doiTacId) {
        DoiTac doiTac = doiTacRepository.findById(doiTacId).orElse(null);
        return doiTac.getSoDu();
    }
    public void truSoDu(Long doiTacId, BigDecimal soTien) {
        DoiTac doiTac = doiTacRepository.findById(doiTacId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đối tác"));
        BigDecimal soDuHienTai = doiTac.getSoDu();
        if (soDuHienTai.compareTo(soTien) < 0) {
            throw new RuntimeException("Số dư không đủ");
        }
        doiTac.setSoDu(soDuHienTai.subtract(soTien));
        doiTacRepository.save(doiTac);
    }
}
