package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.User;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User>  findByUserId(Long userId);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
