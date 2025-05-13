package com.example.IS216_Dlegent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.DoiTac;

@Repository
public interface DoiTacRepository extends JpaRepository<DoiTac, Long> {
    public Optional<DoiTac> findByAccount(Account account);
}
