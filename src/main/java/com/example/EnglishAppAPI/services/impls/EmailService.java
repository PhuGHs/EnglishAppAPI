package com.example.EnglishAppAPI.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendEmail(String emailTo, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setText(body);
            message.setTo(emailTo);
            message.setSubject(subject);

            mailSender.send(message);
            System.out.println("mail sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
