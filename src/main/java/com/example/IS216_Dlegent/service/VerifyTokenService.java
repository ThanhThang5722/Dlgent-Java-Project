package com.example.IS216_Dlegent.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.AccountToken;
import com.example.IS216_Dlegent.repository.AccountTokenRepo;

@Service
public class VerifyTokenService {

    private final AccountTokenRepo accountTokenRepository;

    public VerifyTokenService(AccountTokenRepo accountTokenRepository) {
        this.accountTokenRepository = accountTokenRepository;
    }

    public boolean isValidToken(String token) {
        Optional<AccountToken> accountTokenOpt = accountTokenRepository.findByTokenValue(token);
        return accountTokenOpt.isPresent();
    }
}
