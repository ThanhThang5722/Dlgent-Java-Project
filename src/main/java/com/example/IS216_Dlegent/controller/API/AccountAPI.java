package com.example.IS216_Dlegent.controller.API;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.Mail;
import com.example.IS216_Dlegent.payload.request.UserRegistrationDTO;
import com.example.IS216_Dlegent.payload.dto.PartnerRegistrationDTO;
import com.example.IS216_Dlegent.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/account")
public class AccountAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private DoiTacService doiTacService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/customer")
    public ResponseEntity<?> dangKyTaiKhoanKhach(@RequestBody UserRegistrationDTO request) {
        try {
            userService.SignUp(request);
            request.setPassword("******");

            // Gửi email xác nhận - Viết email service
            /*Mail mail = new Mail();
            mail.setMailFrom("hackathonvietnam.gdgocsr@gmail.com");
            mail.setMailTo("zero2272005@gmail.com");
            mail.setMailSubject("Spring Boot - Email demo");

            String htmlContent = "<h2>Xác nhận địa chỉ email</h2>" +
                "<p>Nhấn nút bên dưới để xác nhận:</p>" +
                "<a href=\"" + confirmationUrl + "\" " +
                "style=\"padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px;\">" +
                "Xác nhận Email</a>";

            mail.setMailContent(htmlContent, true);
            mail.setMailContent("Just testing");
            mailService.sendEmail(mail);*/
            Mail mail = new Mail();
            mail.setMailFrom("nthanhthangclone001@gmail.com");
            mail.setMailTo("zero2272005@gmail.com");
            mail.setMailSubject("DLEGENT - XÁC NHẬN ĐỊA CHỈ EMAIL");    
            
            String confirmationUrl = "http://localhost:8080/verify-email-success?username=" + request.getUsername();
            emailService.sendConfirmationEmail(mail, confirmationUrl);

            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo tài khoản thành công, vui lòng xác nhận email");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/partner")
    public ResponseEntity<?> dangKyTaiKhoanDoiTac(@RequestBody PartnerRegistrationDTO request) {
        try {
            doiTacService.SignUp(request);
            request.setPassword("******");

            
            return ResponseEntity.status(HttpStatus.CREATED).body(request); 
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //@GetMapping("/verify-email")
    //public 
}
