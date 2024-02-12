package by.itacademy.user.service.api;

import by.itacademy.user.core.dto.*;
import by.itacademy.user.core.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    Page<UserEntity> getAllUsers(Pageable pageable);
    UserEntity findUserByUUID(UUID uuid);
    UserEntity saveUser(UserCreateDto userCreateDto);
    UserEntity createUserByAdmin(UserCreateDto userCreateDto);
    UserEntity updateUser(UserCreateDto userCreateDto, UUID uuid, LocalDateTime dtUpdate);
    void activateUser(String email);
}
