package by.itacademy.user.core.dto;

import by.itacademy.user.core.enums.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserAuditDTO {
    private UUID uuid;
    private String mail;
    private String fio;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
