package by.itacademy.audit.core.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginDTO {
    private String mail;
    private String password;
}
