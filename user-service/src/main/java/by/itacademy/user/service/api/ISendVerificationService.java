package by.itacademy.user.service.api;

public interface ISendVerificationService {
    void send(String emailTo, String subject, String text);
}
