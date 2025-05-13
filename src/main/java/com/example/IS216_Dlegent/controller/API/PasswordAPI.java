package com.example.IS216_Dlegent.controller.API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.request.PasswordChangeRequest;
import com.example.IS216_Dlegent.payload.respsonse.PasswordChangeResponse;
import com.example.IS216_Dlegent.service.AccountService;

@RestController
@RequestMapping("/api")
public class PasswordAPI {
    
    @Autowired
    private AccountService accountService;
    
    private final Logger logger = LoggerFactory.getLogger(PasswordAPI.class);
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) {
        logger.info("Received password change request for user ID: {}", request.getUserId());
        
        PasswordChangeResponse response = accountService.changePassword(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
