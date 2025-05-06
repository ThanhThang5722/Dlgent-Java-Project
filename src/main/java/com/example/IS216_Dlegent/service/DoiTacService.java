package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.payload.dto.DoiTacDTO;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.UserRepo;

@Service
public class DoiTacService {
    @Autowired
    private DoiTacRepository doiTacRepository;
    @Autowired
    private UserRepo userRepository;

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

    public List<DoiTacDTO> getDoiTacDTO() {
        List<DoiTac> doiTacs = doiTacRepository.findAll();
        List<DoiTacDTO> doiTacDTOs = new ArrayList<DoiTacDTO>();

        for (DoiTac doiTac : doiTacs) {
            Optional<User> user = userRepository.findByUserId(doiTac.getAccount().getUserId());
            doiTacDTOs.add(new DoiTacDTO(
                    doiTac.getId(),
                    doiTac.getDiaChi(),
                    doiTac.getAccount().getUsername(),
                    doiTac.getAccount().getStatus(),
                    user.get().getFullName(),
                    user.get().getEmail(),
                    user.get().getPhoneNumber(),
                    user.get().getCccd(),
                    user.get().getIsDeleted()));
        }

        return doiTacDTOs;
    }

    public DoiTacDTO getDoiTacById(Long id) {
        Optional<DoiTac> doiTac = doiTacRepository.findById(id);
        Optional<User> user = userRepository.findByUserId(doiTac.get().getAccount().getUserId());
        DoiTacDTO doiTacDTO = new DoiTacDTO(
                doiTac.get().getId(),
                doiTac.get().getDiaChi(),
                doiTac.get().getAccount().getUsername(),
                doiTac.get().getAccount().getStatus(),
                user.get().getFullName(),
                user.get().getEmail(),
                user.get().getPhoneNumber(),
                user.get().getCccd(),
                user.get().getIsDeleted());
        return doiTacDTO;
    }
}
