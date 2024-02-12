package by.itacademy.audit.repository;

import by.itacademy.audit.core.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditEntity, UUID> {
    List<AuditEntity> findAll();
    Optional<AuditEntity> findById(UUID uuid);
}
