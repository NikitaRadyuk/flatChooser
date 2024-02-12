package by.itacademy.user.service;

import by.itacademy.user.core.dto.UserCreateDto;
import by.itacademy.user.core.entity.UserEntity;
import by.itacademy.user.core.enums.ActionForAudit;
import by.itacademy.user.core.enums.EntityForAudit;
import by.itacademy.user.core.enums.UserRole;
import by.itacademy.user.core.enums.UserStatus;
import by.itacademy.user.core.exceptions.ValidationException;
import by.itacademy.user.repository.UserRepository;
import by.itacademy.user.service.api.ISendVerificationService;
import by.itacademy.user.service.api.IUserService;
import by.itacademy.user.service.api.IVerificationService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final IVerificationService verificationService;
    private final ISendVerificationService sendVerificationService;
    private final MailMessageBuilder mailMessageBuilder;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, IVerificationService verificationService, ISendVerificationService sendVerificationService, MailMessageBuilder mailMessageBuilder, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.verificationService = verificationService;
        this.sendVerificationService = sendVerificationService;
        this.mailMessageBuilder = mailMessageBuilder;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    //@Auditor(action = ActionForAudit.INFO_ABOUT_ALL_USERS, entity = EntityForAudit.ADMIN)
    public Page<UserEntity> getAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    //@Auditor(action = ActionForAudit.INFO_ABOUT_USER_BY_ID, entity = EntityForAudit.ADMIN)
    public UserEntity findUserByUUID(UUID uuid) {
        return modelMapper.map(this.userRepository.findById(uuid), UserEntity.class);
    }

    @Transactional
    @Override
    public UserEntity saveUser(UserCreateDto userCreateDto) {
        if(!userRepository.existsByMail(userCreateDto.getMail())) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid(UUID.randomUUID());
            userEntity.setDtCreate(LocalDateTime.now());
            userEntity.setDtUpdate(LocalDateTime.now());
            userEntity.setUserRole(UserRole.USER);
            userEntity.setMail(userCreateDto.getMail());
            userEntity.setUserStatus(UserStatus.WAITING_ACTIVATION);
            userEntity.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
            userEntity.setFio(userCreateDto.getFio());
            this.userRepository.saveAndFlush(userEntity);

            String token = verificationService.createToken(userEntity.getMail());
            sendVerificationService.send(userCreateDto.getMail(), "Verification", mailMessageBuilder.buildMessage(userCreateDto.getMail(), token));
            return userEntity;
        } else{
            throw new ValidationException();
        }
    }

    @Override
    //@Auditor(action = ActionForAudit.SAVE_USER, entity = EntityForAudit.ADMIN)
    public UserEntity createUserByAdmin(UserCreateDto userCreateDto) {
        if(userRepository.existsByMail(userCreateDto.getMail())){
            throw new ValidationException();
        }
        UserEntity user = modelMapper.map(userCreateDto, UserEntity.class);
            user.setUuid(UUID.randomUUID());
            user.setDtCreate(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    @Transactional
    //@Auditor(action = ActionForAudit.UPDATE_USER, entity = EntityForAudit.ADMIN)
    public UserEntity updateUser(UserCreateDto userCreateDto, UUID uuid, LocalDateTime dtUpdate) {
        Optional<UserEntity> userEntity = userRepository.findById(uuid);
        userEntity.get()
                .setMail(userCreateDto.getMail());
        userEntity.get().setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        userEntity.get().setFio(userCreateDto.getFio());
        userEntity.get().setUserRole(userCreateDto.getRole());
        userEntity.get().setUserStatus(userCreateDto.getStatus());
        if (userEntity.get().getDtUpdate().truncatedTo(ChronoUnit.MILLIS).isEqual(dtUpdate)) {
            userRepository.saveAndFlush(userEntity.get());
        } else {
            throw new ValidationException();
        }
        return userEntity.get();
    }

    @Override
    public void activateUser(String mail) {
        Optional<UserEntity> user = userRepository.findByMail(mail);
        if(!user.get().getMail().equals(UserStatus.ACTIVATED)){
            user.get().setUserStatus(UserStatus.ACTIVATED);
            userRepository.saveAndFlush(user.get());
        }else {
            throw new ValidationException();
        }
    }
}
