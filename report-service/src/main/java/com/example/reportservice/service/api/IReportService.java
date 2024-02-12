package com.example.reportservice.service.api;

import com.example.reportservice.core.Status;
import com.example.reportservice.core.Type;
import com.example.reportservice.core.dto.UserActionAuditDTO;
import com.example.reportservice.core.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.UUID;

public interface IReportService {
    void create(Type type, UserActionAuditDTO params);

    Page<ReportEntity> getAllReports(Pageable pageable);

    Status getStatusById(String id);

    ResponseEntity<String> save(UUID uuid) throws IOException;
}
