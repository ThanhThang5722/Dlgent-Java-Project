package com.example.IS216_Dlegent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.KhachHangRepository;
import com.example.IS216_Dlegent.repository.UserRepo;
import com.example.IS216_Dlegent.payload.dto.KhachHangDTO;

@Service
public class KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private AccountRepo accountRepository;

    public List<KhachHangDTO> getKhachHangDTO() {
        List<KhachHang> khachHangs = khachHangRepository.findAll();
        List<KhachHangDTO> khachHangDTOs = new ArrayList<KhachHangDTO>();

        for (KhachHang khachHang : khachHangs) {
            Optional<User> user = userRepository.findByUserId(khachHang.getTaiKhoan().getUserId());

            if (user.isPresent() == false) {
                continue;
            }

            khachHangDTOs.add(new KhachHangDTO(
                    khachHang.getId(),
                    khachHang.getDiaChi(),
                    khachHang.getTaiKhoan().getUsername(),
                    khachHang.getTaiKhoan().getStatus(),
                    khachHang.getTaiKhoan().getCreatedAt(),
                    user.get().getFullName(),
                    user.get().getEmail(),
                    user.get().getPhoneNumber(),
                    user.get().getCccd(),
                    user.get().getIsDeleted()));

        }
        return khachHangDTOs;
    }

    public Optional<KhachHangDTO> getKhachHangDTOById(Long id) {
        Optional<KhachHang> khachHangOpt = khachHangRepository.findById(id);

        if (!khachHangOpt.isPresent()) {
            return Optional.empty();
        }

        KhachHang khachHang = khachHangOpt.get();

        if (khachHang.getTaiKhoan() == null || khachHang.getTaiKhoan().getUserId() == null) {
            return Optional.empty();
        }

        Optional<User> userOpt = userRepository.findByUserId(khachHang.getTaiKhoan().getUserId());

        if (!userOpt.isPresent()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        Account account = khachHang.getTaiKhoan();

        // Không lọc ra khách hàng đã bị xóa
        // if (user.getIsDeleted() != null && user.getIsDeleted() == 1) {
        // return Optional.empty();
        // }

        KhachHangDTO dto = new KhachHangDTO(
                khachHang.getId(),
                khachHang.getDiaChi(),
                account.getUsername(),
                account.getStatus(),
                account.getCreatedAt(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCccd(),
                user.getIsDeleted());

        return Optional.of(dto);
    }

    public ResponseEntity<?> suaKhachHang(Long id, KhachHangDTO khachHangDTO) {
        try {
            Optional<KhachHang> khachHangOpt = khachHangRepository.findById(id);

            if (!khachHangOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            KhachHang khachHang = khachHangOpt.get();
            Account account = khachHang.getTaiKhoan();

            if (account == null || account.getUserId() == null) {
                return ResponseEntity.badRequest().body("Tài khoản không hợp lệ");
            }

            Optional<User> userOpt = userRepository.findByUserId(account.getUserId());

            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Không tìm thấy thông tin người dùng");
            }

            User user = userOpt.get();

            // Cập nhật thông tin từ DTO
            khachHang.setDiaChi(khachHangDTO.getDiaChi());

            if (khachHangDTO.getTenTaiKhoan() != null) {
                account.setUsername(khachHangDTO.getTenTaiKhoan());
            }

            if (khachHangDTO.getTrangThai() != null) {
                account.setStatus(khachHangDTO.getTrangThai());
            }

            if (khachHangDTO.getHoTen() != null) {
                user.setFullName(khachHangDTO.getHoTen());
            }

            if (khachHangDTO.getEmail() != null) {
                user.setEmail(khachHangDTO.getEmail());
            }

            user.setPhoneNumber(khachHangDTO.getSdt());

            if (khachHangDTO.getCccd() != null) {
                user.setCccd(khachHangDTO.getCccd());
            }

            if (khachHangDTO.getIsDeleted() != null) {
                user.setIsDeleted(khachHangDTO.getIsDeleted());
            }

            // Lưu các thay đổi
            khachHangRepository.save(khachHang);
            accountRepository.save(account);
            userRepository.save(user);

            return ResponseEntity.ok("Cập nhật thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật thông tin: " + e.getMessage());
        }
    }

    public ResponseEntity<?> xoaKhachHang(Long id) {
        Optional<KhachHang> khachHangOpt = khachHangRepository.findById(id);

        if (!khachHangOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        KhachHang khachHang = khachHangOpt.get();
        Optional<User> user = userRepository.findByUserId(khachHang.getTaiKhoan().getUserId());

        user.get().setIsDeleted(1);

        userRepository.save(user.get());

        return ResponseEntity.ok("Xóa thành công");
    }
}
