package by.itacademy.user.core.dto;

import by.itacademy.user.core.dto.interfaces.Mailable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VerificationDTO implements Mailable {
    private String code;
    private String mail;
}
