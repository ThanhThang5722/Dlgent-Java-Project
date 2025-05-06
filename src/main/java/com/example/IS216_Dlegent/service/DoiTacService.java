package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.payload.dto.DoiTacDTO;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.UserRepo;

@Service
public class DoiTacService {
    @Autowired
    private DoiTacRepository doiTacRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private AccountRepo accountRepository;

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

    public ResponseEntity<?> suaDoiTacById(Long id, DoiTacDTO doiTacDTO) {
        try {
            Optional<DoiTac> doiTacOpt = doiTacRepository.findById(id);

            if (!doiTacOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            DoiTac doiTac = doiTacOpt.get();
            Account account = doiTac.getAccount();

            if (account == null || account.getUserId() == null) {
                return ResponseEntity.badRequest().body("Tài khoản không hợp lệ");
            }

            Optional<User> userOpt = userRepository.findByUserId(account.getUserId());

            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Không tìm thấy thông tin người dùng");
            }

            User user = userOpt.get();

            if (doiTacDTO.getDiaChi() != null) {
                doiTac.setDiaChi(doiTacDTO.getDiaChi());
            }
            if (doiTacDTO.getTenTaiKhoan() != null) {
                account.setUsername(doiTacDTO.getTenTaiKhoan());
            }

            if (doiTacDTO.getTrangThai() != null) {
                account.setStatus(doiTacDTO.getTrangThai());
            }

            if (doiTacDTO.getHoTen() != null) {
                user.setFullName(doiTacDTO.getHoTen());
            }

            if (doiTacDTO.getEmail() != null) {
                user.setEmail(doiTacDTO.getEmail());
            }

            if (doiTacDTO.getSdt() != null) {
                user.setPhoneNumber(doiTacDTO.getSdt());
            }

            if (doiTacDTO.getCccd() != null) {
                user.setCccd(doiTacDTO.getCccd());
            }

            if (doiTacDTO.getIsDeleted() != null) {
                user.setIsDeleted(doiTacDTO.getIsDeleted());
            }

            doiTacRepository.save(doiTac);
            accountRepository.save(account);
            userRepository.save(user);

            return ResponseEntity.ok("Cập nhật thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật thông tin: " + e.getMessage());
        }
    }

    public ResponseEntity<?> xoaDoiTacById(Long id) {
        Optional<DoiTac> doiTacOpt = doiTacRepository.findById(id);

        if (!doiTacOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DoiTac doiTac = doiTacOpt.get();
        Optional<User> user = userRepository.findByUserId(doiTac.getAccount().getUserId());

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        user.get().setIsDeleted(1);
        userRepository.save(user.get());

        return ResponseEntity.ok("Xóa thành công");
    }
}
