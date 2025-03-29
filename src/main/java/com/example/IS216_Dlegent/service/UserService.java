package com.example.IS216_Dlegent.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.repository.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public static void Sigin() {

    }
}
