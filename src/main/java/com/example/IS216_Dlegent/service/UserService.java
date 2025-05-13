package com.example.IS216_Dlegent.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.encoder.Sha256PasswordEncoder;
import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.AccountRoleGroup;
import com.example.IS216_Dlegent.model.Customer;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.payload.request.UserRegistrationDTO;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.AccountRoleGroupRepo;
import com.example.IS216_Dlegent.repository.CustomerRepository;
import com.example.IS216_Dlegent.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private final AccountRepo accountRepo;
    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private AccountRoleGroupRepo accountRoleGroupRepo;

    @Autowired
    private CustomerRepository customerRepository;

    public UserService(UserRepo userRepository, AccountRepo accountRepo) {
        this.userRepo = userRepository;
        this.accountRepo = accountRepo;
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public void SignIn() {

    }

    public void SignUp(UserRegistrationDTO info) {
        /*Optional<Customer> optionalCustomer = new //customerRepository.findByAccountUsername(info.getUsername());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if(customer.getTinhTrang().equals("ACTIVE")) {
                throw new RuntimeException("Tài khoản đã tồn tại!");
            }
        }*/
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


        // Tạo mới Khách hàng
        Customer customer = new Customer();
        customer.setAccount(account);
        customer.setAddress(info.getAddress());
        customer.setLoyaltyPoints(0);
        customer.setTinhTrang("INCONFIRM");
        customerRepository.save(customer);

        // 1 -> ADMIN
        // 2 -> PARTNER
        // 3 -> CUSTOMER
        AccountRoleGroup accountRoleGroup = new AccountRoleGroup(account.getAccountId(), 3L);
        accountRoleGroupRepo.saveAccountRoleGroup(accountRoleGroup);
    }


}
