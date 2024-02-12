package by.itacademy.user.service.api;

import by.itacademy.user.core.dto.UserDTO;
import by.itacademy.user.core.dto.UserLoginDTO;
import by.itacademy.user.core.dto.UserRegDTO;
import by.itacademy.user.core.dto.VerificationDTO;
import by.itacademy.user.core.entity.UserEntity;

public interface IAuthorizationService {
    UserEntity registrUser(UserRegDTO userRegDTO);
    String loginUser(UserLoginDTO userLoginDTO);
    UserDTO getInfoAboutMe();
    void verifyUserByToken(VerificationDTO verificationDTO);
}
