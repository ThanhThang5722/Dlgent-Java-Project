package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.encoder.Sha256PasswordEncoder;
import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.AccountRoleGroup;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.payload.request.UserRegistrationDTO;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.AccountRoleGroupRepo;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.PartnerRegistrationDTO;
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
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRoleGroupRepo accountRoleGroupRepo;
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
    public void SignIn() {

    }

    public void SignUp(PartnerRegistrationDTO info) {
        if(userRepo.existsByEmail(info.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        if (userRepo.existsByPhoneNumber(info.getPhoneNumber())) {
            throw new RuntimeException("Số điện thoại đã tồn tại!");
        }
        if (userRepo.existsByCccd(info.getCccd())) {
            throw new RuntimeException("CCCD đã tồn tại!");
        }

        if (accountRepo.existsByUsername(info.getUsername())) {
            throw new RuntimeException("Tên tài khoản đã tồn tại!");
        }

        // Tạo mới User
        User user = new User();
        user.setFullName(info.getFullName());
        user.setEmail(info.getEmail());
        user.setPhoneNumber(info.getPhoneNumber());
        user.setCccd(info.getCccd());
        user.setCreatedAt(new Date());
        user.setIsDeleted(0);
        user = userRepo.save(user);

        // Tạo mới Account
        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setUsername(info.getUsername());
        account.setPassword(Sha256PasswordEncoder.encode(info.getPassword())); // mã hóa mật khẩu
        account.setStatus("ACTIVE");
        account.setCreatedAt(new Date());
        accountRepo.save(account);

        // Tạo mới Partner
        DoiTac doiTac = new DoiTac();
        doiTac.setAccount(account);
        doiTac.setDiaChi(info.getAddress());
        doiTac.setSoDu(BigDecimal.ZERO);
        doiTac.setTaiKhoanNganHang(info.getBankAccountNumber());
        doiTac.setTenNganHang(info.getBankName());
        doiTac.setTenTaiKhoanNganHang(info.getBankAccountName());
        doiTac.setTinhTrang("INVALID");
        doiTacRepository.save(doiTac);

        // 1 -> ADMIN
        // 2 -> PARTNER
        // 3 -> CUSTOMER
        AccountRoleGroup accountRoleGroup = new AccountRoleGroup(account.getAccountId(), 2L);
        accountRoleGroupRepo.saveAccountRoleGroup(accountRoleGroup);
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

    public ResponseEntity<List<DoiTacDTO>> getDoiTacApproval() {
        List<DoiTac> doiTacs = doiTacRepository.findAll();
        List<DoiTacDTO> doiTacDTOs = new ArrayList<DoiTacDTO>();

        for (DoiTac doiTac : doiTacs) {
            Optional<User> user = userRepository.findByUserId(doiTac.getAccount().getUserId());

            if (!user.isPresent() || user.get().getIsDeleted() == 1) {
                continue;
            }

            if (doiTac.getAccount().getStatus().equals("PENDING")) {
                doiTacDTOs.add(new DoiTacDTO(
                        doiTac.getId(),
                        doiTac.getDiaChi(),
                        doiTac.getTaiKhoanNganHang(),
                        doiTac.getTenNganHang(),
                        doiTac.getTenTaiKhoanNganHang(),
                        doiTac.getAccount().getUsername(),
                        doiTac.getAccount().getStatus(),
                        user.get().getFullName(),
                        user.get().getEmail(),
                        user.get().getPhoneNumber(),
                        user.get().getCccd()));
            }
        }

        return ResponseEntity.ok(doiTacDTOs);
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
