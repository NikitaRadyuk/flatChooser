package by.itacademy.user.core.dto;

import by.itacademy.user.core.enums.ActionForAudit;
import by.itacademy.user.core.enums.EntityForAudit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class AuditDTO {
    private UUID uuid = UUID.randomUUID();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime dtCreate = LocalDateTime.now();
    private UserAuditDTO userAuditDTO;
    private ActionForAudit actionForAudit;
    private EntityForAudit entityForAudit;
    private String id;
}
