package com.example.IS216_Dlegent.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.IS216_Dlegent.encoder.Sha256PasswordEncoder;
import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.payload.request.LoginRequest;
import com.example.IS216_Dlegent.service.AccountService;


@Controller
public class SignInController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/signin")
    public String GetSignInView(HttpServletRequest request) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        request.setAttribute("bootstrapUrl", bootstrapUrl);
        return "signin";
    }
    
    @PostMapping("/signin")
    public String SubmitUsernamePassword(@RequestBody LoginRequest userLogin) {
        /*Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("User Login Request: {}", userLogin.toString());

        String hashPass = Sha256PasswordEncoder.encode(userLogin.getPassword());
        logger.info("Hash pass of UserLogin: {}", hashPass);
        Account testAcc = new Account();
        testAcc.setPassword("932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef");
        logger.info("Password is equal: {}", hashPass.equals(testAcc.getPassword()));*/

        boolean isValid = accountService.verifyAccount(userLogin);
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Is Exist: {}", isValid);

        return "index";
    }
}
