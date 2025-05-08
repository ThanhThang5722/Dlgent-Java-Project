package com.example.IS216_Dlegent.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.RoleGroup;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.KhachHangRepository;
import com.example.IS216_Dlegent.repository.UserRepo;

/**
 * Để sử dụng class này, hãy bỏ comment annotation @Component
 * và chạy ứng dụng. Class này sẽ tự động tạo 5 tài khoản khách hàng
 * và 5 tài khoản đối tác khi ứng dụng khởi động.
 * 
 * Sau khi chạy xong, hãy comment lại annotation @Component để tránh
 * tạo dữ liệu trùng lặp khi khởi động lại ứng dụng.
 */
// @Component
public class CreateTestAccounts implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private KhachHangRepository khachHangRepo;

    @Autowired
    private DoiTacRepository doiTacRepo;

    // Mật khẩu mặc định: "password" đã được hash với SHA-256
    private static final String DEFAULT_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

    @Override
    public void run(String... args) throws Exception {
        // Tạo 5 khách hàng
        createCustomers();
        
        // Tạo 5 đối tác
        createPartners();
        
        System.out.println("Đã tạo xong 5 tài khoản khách hàng và 5 tài khoản đối tác!");
    }
    
    private void createCustomers() {
        // Tìm role CUSTOMER
        RoleGroup customerRole = accountRepo.findAll().stream()
                .filter(a -> a.getRoleGroups().stream()
                        .anyMatch(r -> r.getGroupName().equals("CUSTOMER")))
                .findFirst()
                .map(a -> a.getRoleGroups().stream()
                        .filter(r -> r.getGroupName().equals("CUSTOMER"))
                        .findFirst().orElse(null))
                .orElse(null);
        
        if (customerRole == null) {
            System.err.println("Không tìm thấy role CUSTOMER!");
            return;
        }
        
        // Tạo 5 khách hàng
        for (int i = 1; i <= 5; i++) {
            // Tạo User
            User user = new User();
            user.setFullName("Khách hàng " + i);
            user.setEmail("customer" + i + "@example.com");
            user.setPhoneNumber("090123456" + i);
            user.setCccd("07912345678" + i);
            user.setCreatedAt(new Date());
            user.setIsDeleted(0);
            user = userRepo.save(user);
            
            // Tạo Account
            Account account = new Account();
            account.setUserId(user.getUserId());
            account.setUsername("customer" + i);
            account.setPassword(DEFAULT_PASSWORD);
            account.setStatus("ACTIVE");
            account.setCreatedAt(new Date());
            
            // Gán role
            Set<RoleGroup> roles = new HashSet<>();
            roles.add(customerRole);
            account.setRoleGroups(roles);
            
            account = accountRepo.save(account);
            
            // Tạo KhachHang
            KhachHang khachHang = new KhachHang();
            khachHang.setTaiKhoan(account);
            khachHang.setDiaChi("Địa chỉ khách hàng " + i);
            khachHang.setDiemTichLuy(0);
            
            khachHangRepo.save(khachHang);
        }
    }
    
    private void createPartners() {
        // Tìm role PARTNER
        RoleGroup partnerRole = accountRepo.findAll().stream()
                .filter(a -> a.getRoleGroups().stream()
                        .anyMatch(r -> r.getGroupName().equals("PARTNER")))
                .findFirst()
                .map(a -> a.getRoleGroups().stream()
                        .filter(r -> r.getGroupName().equals("PARTNER"))
                        .findFirst().orElse(null))
                .orElse(null);
        
        if (partnerRole == null) {
            System.err.println("Không tìm thấy role PARTNER!");
            return;
        }
        
        // Tạo 5 đối tác
        for (int i = 1; i <= 5; i++) {
            // Tạo User
            User user = new User();
            user.setFullName("Đối tác " + i);
            user.setEmail("partner" + i + "@example.com");
            user.setPhoneNumber("090987654" + i);
            user.setCccd("07987654321" + i);
            user.setCreatedAt(new Date());
            user.setIsDeleted(0);
            user = userRepo.save(user);
            
            // Tạo Account
            Account account = new Account();
            account.setUserId(user.getUserId());
            account.setUsername("partner" + i);
            account.setPassword(DEFAULT_PASSWORD);
            account.setStatus("ACTIVE");
            account.setCreatedAt(new Date());
            
            // Gán role
            Set<RoleGroup> roles = new HashSet<>();
            roles.add(partnerRole);
            account.setRoleGroups(roles);
            
            account = accountRepo.save(account);
            
            // Tạo DoiTac
            DoiTac doiTac = new DoiTac();
            doiTac.setAccount(account);
            doiTac.setDiaChi("Địa chỉ đối tác " + i);
            doiTac.setTaiKhoanNganHang("10000000" + i);
            doiTac.setTenTaiKhoanNganHang("Tên TK NH " + i);
            doiTac.setTenNganHang("Ngân hàng " + i);
            doiTac.setSoDu(BigDecimal.ZERO);
            
            doiTacRepo.save(doiTac);
        }
    }
}
