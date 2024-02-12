package by.itacademy.user.service.api;

public interface IVerificationService {
    String createToken(String email);
    String getMailByToken(String token);

}
