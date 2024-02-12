package by.itacademy.user.clients;

import by.itacademy.user.core.dto.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
@FeignClient(name = "audit-logs", url = "http://localhost:8084/audit")
public interface AuditFeignClient {
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    AuditDTO sendRequestToCreateLog(@RequestHeader String AUTHORIZATION,
                                    @RequestBody AuditDTO auditDTO);
}
