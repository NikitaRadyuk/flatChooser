package by.itacademy.user.core.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRegDTO {
    private String mail;
    private String fio;
    private String password;
}
