package by.itacademy.audit.service.api;

import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IAuditService {
    Page<AuditEntity> getAudit(Pageable pageable);
    Optional<AuditEntity> getAuditById(UUID id);
    AuditEntity saveAudit(AuditDTO auditDTO);
}
