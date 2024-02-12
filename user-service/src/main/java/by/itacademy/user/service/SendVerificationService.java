package by.itacademy.user.service;

import by.itacademy.user.service.api.ISendVerificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class SendVerificationService implements ISendVerificationService {
    private final JavaMailSender mailSender;

    public SendVerificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("{spring.mail.username}")
    private String username;

    @Async
    public void send(String emailTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
