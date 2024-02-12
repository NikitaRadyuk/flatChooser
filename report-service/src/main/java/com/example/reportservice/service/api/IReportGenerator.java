package com.example.reportservice.service.api;

import com.example.reportservice.core.entity.AuditEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IReportGenerator {
    void generate(List<AuditEntity> reports, UUID name) throws IOException;
}
