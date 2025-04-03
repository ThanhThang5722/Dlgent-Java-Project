package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.AccountToken;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.AccountTokenRepo;

@Service
public class VerifyTokenService {

    private final AccountTokenRepo accountTokenRepository;
    private final AccountRepo accountRepo;

    public VerifyTokenService(AccountTokenRepo accountTokenRepository, AccountRepo accountRepo) {
        this.accountTokenRepository = accountTokenRepository;
        this.accountRepo = accountRepo;
    }

    public boolean isValidToken(String token) {
        Optional<AccountToken> accountTokenOpt = accountTokenRepository.findByTokenValue(token);
        if(accountTokenOpt.isPresent()) {
            AccountToken accountToken = accountTokenOpt.get();
            Date currentTime = new Date();
            
            return currentTime.before(accountToken.getExpiresAt());
        }
        return false;
    }

    public boolean isPartner(String token) {
        Optional<AccountToken> accountTokenOpt = accountTokenRepository.findByTokenValue(token);
        if(accountTokenOpt.isPresent()) {
            AccountToken accountToken = accountTokenOpt.get();
            Long accountId = accountToken.getAccountId();
            return hasRole(accountId, "PARTNER");
        }
        return false;
    }

    public boolean hasRole(Long accountId, String roleGroupName) {
        BigDecimal result = accountRepo.haveRole(accountId, roleGroupName);
        return result != null && result.intValue() == 1;
    }
}
