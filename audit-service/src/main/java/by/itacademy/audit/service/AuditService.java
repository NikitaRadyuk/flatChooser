package by.itacademy.audit.service;

import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.entity.AuditEntity;
import by.itacademy.audit.repository.AuditRepository;
import by.itacademy.audit.service.api.IAuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class AuditService implements IAuditService {
    private final AuditRepository repository;

    public AuditService(AuditRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<AuditEntity> getAudit(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Optional<AuditEntity> getAuditById(UUID id) {
        return this.repository.findById(id);
    }

    @Override
    public AuditEntity saveAudit(AuditDTO dto) {
        AuditEntity entity = new AuditEntity();
        entity.setId(dto.getUuid());
        entity.setDtCreate(dto.getDtCreate());
        entity.setUuid(dto.getUserAuditDTO().getUuid());
        entity.setMail(dto.getUserAuditDTO().getMail());
        entity.setFio(dto.getUserAuditDTO().getFio());
        entity.setRole(dto.getUserAuditDTO().getRole());
        entity.setText(dto.getAction());
        entity.setEssenceType(dto.getType());
        entity.setEssenceId(dto.getTypeId());
        return this.repository.save(entity);
    }
}
