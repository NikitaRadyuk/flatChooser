package by.itacademy.user.core.dto;

import by.itacademy.user.core.enums.UserRole;
import by.itacademy.user.core.dto.interfaces.Userable;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserDetailsDTO implements Userable {
    private UUID uuid;
    private String mail;
    private String fio;
    private UserRole role;
}
