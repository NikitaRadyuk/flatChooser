package by.itacademy.user.service;

import by.itacademy.user.controller.utils.JwtTokenHandler;
import by.itacademy.user.core.dto.*;
import by.itacademy.user.core.entity.UserEntity;
import by.itacademy.user.core.enums.UserStatus;
import by.itacademy.user.core.exceptions.ValidationException;
import by.itacademy.user.repository.UserRepository;
import by.itacademy.user.service.api.IAuthorizationService;
import by.itacademy.user.service.api.IUserService;
import by.itacademy.user.service.api.IVerificationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthorizationService implements IAuthorizationService {
    private final IUserService userService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHandler jwtTokenHandler;
    private final IVerificationService verificationService;

    public AuthorizationService(IUserService userService, ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenHandler jwtTokenHandler, IVerificationService verificationService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenHandler = jwtTokenHandler;
        this.verificationService = verificationService;
    }

    @Override
    @Transactional
    //@Auditor(action = ActionForAudit.REGISTRATION, entity = EntityForAudit.USER)
    public UserEntity registrUser(UserRegDTO userRegDTO) {
        UserCreateDto userCreateDto = modelMapper.map(userRegDTO, UserCreateDto.class);
        return userService.saveUser(userCreateDto);
    }

    @Transactional
    @Override
    //@Auditor(action = ActionForAudit.LOGIN, entity = EntityForAudit.USER)
    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> entityByMail = userRepository.findByMail(userLoginDTO.getMail());
        UserEntity entity = modelMapper.map(entityByMail, UserEntity.class);

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), entity.getPassword())) {
            throw new ValidationException();
        }
        if (!entity.getUserStatus().equals(UserStatus.ACTIVATED)){
            throw new ValidationException();
        }
        UserDetailsDTO userDetailsDTO = modelMapper.map(entity, UserDetailsDTO.class);
        return jwtTokenHandler.generateAccessToken(userDetailsDTO);
    }
    @Transactional
    @Override
    //@Auditor(action = ActionForAudit.VERIFICATION, entity = EntityForAudit.USER)
    public void verifyUserByToken(VerificationDTO verificationDTO) {
        String mail = verificationService.getMailByToken(verificationDTO.getCode());
        try {
            userService.activateUser(mail);
        }catch (ValidationException exception){
            throw new ValidationException();
        }
    }

    @Override
    //@Auditor(action = ActionForAudit.INFO_ABOUT_ME, entity = EntityForAudit.USER)
    public UserDTO getInfoAboutMe() {
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findById(userDetailsDTO.getUuid());
        return modelMapper.map(userEntity, UserDTO.class);
    }
}
