package by.itacademy.user.core.dto;

import by.itacademy.user.core.enums.UserRole;
import by.itacademy.user.core.enums.UserStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserCreateDto {
    @JsonProperty("mail")
    private String mail;
    @JsonProperty("fio")
    private String fio;
    @JsonProperty("role")
    private UserRole role;
    @JsonProperty("status")
    private UserStatus status;
    @JsonProperty("password")
    private String password;
}
