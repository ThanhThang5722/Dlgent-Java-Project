package com.example.IS216_Dlegent.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.controller.SSR.LichSuRutTienAdminDTO;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.LichSuRutTien;
import com.example.IS216_Dlegent.payload.request.RutTienRequest;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.LichSuRutTienRepository;

@Service
public class LichSuRutTienService {

    @Autowired
    private LichSuRutTienRepository lichSuRutTienRepository;

    @Autowired
    private DoiTacRepository doiTacRepository;

    public LichSuRutTien themYeuCauRutTien(RutTienRequest request) {
        LichSuRutTien entity = new LichSuRutTien();
        entity.setDoiTacId(request.getDoiTacId());
        entity.setSoTien(request.getSoTien());
        entity.setThoiGianTao(LocalDateTime.now());
        entity.setTrangThaiRutTien("Chờ phê duyệt");

        return lichSuRutTienRepository.save(entity);
    }

    public List<LichSuRutTienAdminDTO> getAllRutTienWithDoiTacInfo() {
        List<LichSuRutTien> lichSuList = lichSuRutTienRepository.findAllByOrderByThoiGianTaoDesc();
        List<LichSuRutTienAdminDTO> result = new ArrayList<>();

        for (LichSuRutTien ls : lichSuList) {
            Optional<DoiTac> optionalDoiTac = doiTacRepository.findById(ls.getDoiTacId());
            if (optionalDoiTac.isPresent()) {
                DoiTac doiTac = optionalDoiTac.get();
                LichSuRutTienAdminDTO dto = new LichSuRutTienAdminDTO();
                dto.setId(ls.getId());
                dto.setDoiTacId(doiTac.getId());
                dto.setTenDoiTac(doiTac.getAccount().getUsername()); // hoặc getName tùy theo field
                dto.setSoTien(ls.getSoTien());
                dto.setThoiGianTao(ls.getThoiGianTao());
                dto.setThoiGianRutTien(ls.getThoiGianRutTien());
                dto.setTrangThai(ls.getTrangThaiRutTien());
                dto.setSoDuHienTai(doiTac.getSoDu()); // nếu có
                result.add(dto);
            }
        }
        return result;
    }

}
