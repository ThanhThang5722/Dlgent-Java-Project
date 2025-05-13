package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
        account.setUserId(user.getUserId().intValue());
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
}
