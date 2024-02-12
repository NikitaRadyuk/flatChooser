package by.itacademy.user.core.dto;

import by.itacademy.user.core.enums.UserStatus;
import by.itacademy.user.core.enums.UserRole;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDTO {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("dt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dt_create;

    @JsonProperty("dt_update")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dt_update;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("fio")
    private String fio;

    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

}