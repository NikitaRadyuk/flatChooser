package com.example.reportservice.repository;

import com.example.reportservice.core.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, UUID> {
    @Query("SELECT a FROM AuditEntity AS a WHERE a.id = :uuid AND a.dtCreate BETWEEN :from AND :to")
    List<AuditEntity> findAllByParam(UUID uuid, LocalDateTime from, LocalDateTime to);
}
