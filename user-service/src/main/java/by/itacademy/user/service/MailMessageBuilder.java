package by.itacademy.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailMessageBuilder {
    @Value("${custom.mail.message}")
    private String verificationMessage;

    public String buildMessage(String mail, String token){
        return verificationMessage.formatted(mail, token);
    }
}
