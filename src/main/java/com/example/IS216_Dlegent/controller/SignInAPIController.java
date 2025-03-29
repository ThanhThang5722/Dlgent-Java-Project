package com.example.IS216_Dlegent.controller;

import java.time.Duration;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.encoder.Sha256PasswordEncoder;
import com.example.IS216_Dlegent.payload.request.LoginRequest;
import com.example.IS216_Dlegent.payload.respsonse.LoginResponse;
import com.example.IS216_Dlegent.service.AccountService;

@RestController
public class SignInAPIController {
    @Autowired
    private AccountService accountService;

    private final Logger logger = LoggerFactory.getLogger(SignInController.class);
    
    @PostMapping("/api/signin")
    public ResponseEntity<?> submitUsernamePassword(@RequestBody LoginRequest userLogin) {
        logger.info("Received request: {}", userLogin);
        try {
            logger.info("Login attempt with username: {}", userLogin.getUsername());
            
            boolean isValid = accountService.verifyAccount(userLogin);
            logger.info("Authentication result: {}", isValid);
            
            if(isValid) {
                // Create new token valid for 10 minutes and save to database
                String token = Sha256PasswordEncoder.encode(userLogin.getUsername() + new Date(System.currentTimeMillis()));
                logger.info("Generated token for userc: {}", userLogin.getUsername());
                
                try {
                    accountService.saveToken(userLogin.getUsername(), token, Duration.ofMinutes(10));
                    logger.info("Token saved successfully");
                } catch (Exception e) {
                    logger.error("Error saving token: {}", e.getMessage(), e);
                    return ResponseEntity.status(500).body(new LoginResponse("Error creating session", null));
                }

                ResponseCookie cookie = ResponseCookie.from("auth_token", token)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(Duration.ofMinutes(10))
                        .build();
                
                // Create Return success API and set cookies with the token
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse("Login successful", token));
            }
            
            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password", null));
        } catch (Exception e) {
            logger.error("Error during login process: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new LoginResponse("Server error during login", null));
        }
    }
}
