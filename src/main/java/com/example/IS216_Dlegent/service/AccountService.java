package com.example.IS216_Dlegent.service;

import java.lang.foreign.Linker.Option;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.encoder.Sha256PasswordEncoder;
import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.AccountToken;
import com.example.IS216_Dlegent.model.RoleGroup;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.payload.request.LoginRequest;
import com.example.IS216_Dlegent.payload.request.PasswordChangeRequest;
import com.example.IS216_Dlegent.payload.respsonse.PasswordChangeResponse;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.AccountTokenRepo;
import com.example.IS216_Dlegent.repository.UserRepo;

@Service
public class AccountService {

    private final AccountRepo accountRepository;
    private final AccountTokenRepo accountTokenRepository;
    private final UserRepo userRepo;

    public AccountService(AccountRepo accountRepository, AccountTokenRepo accountTokenRepository, UserRepo userRepo) {
        this.accountRepository = accountRepository;
        this.accountTokenRepository = accountTokenRepository;
        this.userRepo = userRepo;
    }

    // Lấy tài khoản theo ID
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    // Lấy tài khoản theo tên đăng nhập
    public Optional<Account> getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    // Kiểm tra xem tên đăng nhập đã tồn tại chưa
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    // Lưu tài khoản mới hoặc cập nhật tài khoản
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    // Xóa tài khoản (soft delete bằng cách cập nhật trạng thái)
    public void deactivateAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        account.ifPresent(acc -> {
            acc.setStatus("INACTIVE");
            accountRepository.save(acc);
        });
    }

    public boolean verifyAccount(LoginRequest userLogin) {
        Logger logger = LoggerFactory.getLogger(getClass());
        Optional<Account> accountOptional = accountRepository.findByUsername(userLogin.getUsername());
        logger.info("Username: {}", userLogin.getUsername());
        logger.info("Password: {}", userLogin.getPassword());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            String hashPass = Sha256PasswordEncoder.encode(userLogin.getPassword());
            logger.info("DB Hash Password: {}", hashPass);
            return hashPass.equals(account.getPassword());
        }
        return false;
    }

    public void saveToken(String username, String token, Duration duration) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("This is my Token: {}", token);
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        Account account = accountOptional.get();
        AccountToken accountToken = new AccountToken();

        accountToken.setAccountId(account.getAccountId());
        accountToken.setTokenValue(token);
        accountToken.setIssuedAt(new Date(System.currentTimeMillis()));
        accountToken.setExpiresAt(new Date(System.currentTimeMillis() + duration.toMillis()));
        accountToken.setIsRevoked(0); // 0 = active, 1 = revoked

        accountTokenRepository.save(accountToken);
    }

    public boolean isTokenValid(String token) {
        Optional<AccountToken> accountToken = accountTokenRepository.findByTokenValue(token);
        return accountToken.isPresent() &&
                (accountToken.get().getIsRevoked() == 0) &&
                accountToken.get().getExpiresAt().after(new Date());
    }

    public List<RoleGroup> getRoleGroupsByUsername(String username) {
        Optional<Account> accountOpt = accountRepository.findByUsername(username);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("account id: {}", account.getAccountId());
            return new ArrayList<>(account.getRoleGroups()); // Convert Set to List
        } else {
            return new ArrayList<>(); // Return empty list
        }
    }

    public PasswordChangeResponse changePassword(PasswordChangeRequest request) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Changing password for user ID: {}", request.getUserId());

        if (request.getConfirmPassword() == null || !request.getConfirmPassword().equals(request.getNewPassword())) {
            return new PasswordChangeResponse(
                    "Mật khẩu và xác nhận mật khẩu không khớp. Vui lòng kiểm tra lại và nhập lại.", false);
        }

        Optional<Account> accountOpt = accountRepository.findById(request.getUserId());
        if (!accountOpt.isPresent()) {
            return new PasswordChangeResponse("Tài khoản không tồn tại", false);
        }

        Account account = accountOpt.get();

        String currentPasswordHash = Sha256PasswordEncoder.encode(request.getCurrentPassword());
        if (!currentPasswordHash.equals(account.getPassword())) {
            return new PasswordChangeResponse(
                    "Mật khẩu hiện tại bạn đã nhập không chính xác. Vui lòng kiểm tra lại và thử lại.", false);
        }

        String newPasswordHash = Sha256PasswordEncoder.encode(request.getNewPassword());
        account.setPassword(newPasswordHash);
        accountRepository.save(account);

        return new PasswordChangeResponse("Đổi mật khẩu thành công", true);
    }
}
