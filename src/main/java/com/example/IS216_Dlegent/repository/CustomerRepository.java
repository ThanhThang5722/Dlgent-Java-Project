package com.example.IS216_Dlegent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.Customer;

import jakarta.transaction.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.tinhTrang = 'ACTIVE' WHERE c.account.username = :username")
    int activateCustomerByUsername(@Param("username") String username);

    Optional<Customer> findByAccount(Account account);
    //Optional<Customer> findByAccountId(Long accountId);
    //Optional<Customer> findByAccountUsername(String username);
}
