package by.itacademy.user.core.dto;

import by.itacademy.user.core.dto.interfaces.Mailable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoginDTO implements Mailable {
    private String mail;
    private String password;
}
