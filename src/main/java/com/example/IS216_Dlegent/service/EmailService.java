package com.example.IS216_Dlegent.service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.Mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(Mail mail, String confirmationUrl) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mail.getMailFrom());
        helper.setTo(mail.getMailTo());
        helper.setSubject(mail.getMailSubject());

        String htmlTemplate = this.loadEmailTemplate();
        String htmlContent = htmlTemplate.replace("{{CONFIRMATION_LINK}}", confirmationUrl);


        helper.setText(htmlContent, true); // true = gửi HTML
        mailSender.send(message);
    }
    public String loadEmailTemplate() {
        try {
            ClassPathResource resource = new ClassPathResource("templates/MailHTML.html");
            return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Ghi log lỗi hoặc ném runtime exception tùy cách xử lý
            throw new RuntimeException("Không thể đọc template email xác nhận", e);
        }
    }
}
