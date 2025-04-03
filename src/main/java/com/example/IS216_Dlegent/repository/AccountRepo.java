package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query(value = "SELECT haveRole(:accountId, :roleGroupName) FROM dual", nativeQuery = true)
    BigDecimal haveRole(@Param("accountId") Long accountId, @Param("roleGroupName") String roleGroupName);
}