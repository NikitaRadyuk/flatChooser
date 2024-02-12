package com.example.flatservice.repository;

import com.example.flatservice.core.entity.FlatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlatsRepository extends JpaRepository<FlatEntity, UUID> {
    Page<FlatEntity> findAll(Specification<FlatEntity> filter, Pageable pageable);
}
