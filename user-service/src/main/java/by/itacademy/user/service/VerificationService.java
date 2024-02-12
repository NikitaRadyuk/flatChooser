package by.itacademy.user.service;

import by.itacademy.user.core.entity.VerificationEntity;
import by.itacademy.user.core.exceptions.ValidationException;
import by.itacademy.user.repository.VerificationCodeRepository;
import by.itacademy.user.service.api.IVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VerificationService implements IVerificationService {
    private final VerificationCodeRepository verificationCodeRepository;

    public VerificationService(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }
    @Transactional
    @Override
    public String createToken(String mail) {
        String token = UUID.randomUUID().toString();
        VerificationEntity entity = new VerificationEntity(token, mail);
        VerificationEntity res = verificationCodeRepository.save(entity);
        return res.getToken();
    }

    @Override
    public String getMailByToken(String token) {
        String mail = verificationCodeRepository.findMailByToken(token);
        if(mail.isEmpty()){
            throw new ValidationException();
        }
        return mail;
    }
}
