package com.example.IS216_Dlegent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.IS216_Dlegent.model.AccountToken;

public interface AccountTokenRepo extends JpaRepository<AccountToken, Long> {
    Optional<AccountToken> findByTokenValue(String tokenValue);
}
