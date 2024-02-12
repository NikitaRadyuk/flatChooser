package com.example.reportservice.service;


import com.example.reportservice.core.Status;
import com.example.reportservice.core.Type;
import com.example.reportservice.core.dto.UserActionAuditDTO;
import com.example.reportservice.core.entity.AuditEntity;
import com.example.reportservice.core.entity.ReportEntity;
import com.example.reportservice.repository.AuditRepository;
import com.example.reportservice.repository.ReportRepository;
import com.example.reportservice.service.api.IReportGenerator;
import com.example.reportservice.service.api.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;



@Slf4j
@Service
public class ReportService implements IReportService {

    private static final String FILE_DIRECTORY = ".";
    private final ReportRepository reportRepository;
    private final AuditRepository auditRepository;
    private final IReportGenerator reportGenerator;

    public ReportService(ReportRepository reportRepository, AuditRepository auditRepository, IReportGenerator reportGenerator) {
        this.reportRepository = reportRepository;
        this.auditRepository = auditRepository;
        this.reportGenerator = reportGenerator;
    }

    @Override
    public void create(Type type, UserActionAuditDTO params) {
        ReportEntity entity = new ReportEntity();
        entity.setId(UUID.randomUUID());
        entity.setDtCreate(LocalDateTime.now());
        entity.setDtUpdate(entity.getDtCreate());
        entity.setStatus(Status.PROGRESS);
        entity.setType(type);
        entity.setUserId(params.getId());
        entity.setFrom(params.getFrom());
        entity.setTo(params.getTo());
        entity.setDescription(type.getReportTypeId() + " from: " + params.getFrom() + " to: " + params.getTo());

        ReportEntity saveAndFlush = this.reportRepository.saveAndFlush(entity);

        List<AuditEntity> audits = auditRepository.findAllByParam(
                UUID.fromString(params.getId()),
                params.getFrom(),
                params.getTo()
        );

        try {
            reportGenerator.generate(audits, saveAndFlush.getId());
            saveAndFlush.setStatus(Status.DONE);

        } catch (IOException e) {
            log.error("Ошибка создания отчета" + e);
            saveAndFlush.setStatus(Status.ERROR);
        }

        reportRepository.save(saveAndFlush);

    }

    @Override
    public Page<ReportEntity> getAllReports(Pageable pageable) {
        return this.reportRepository.findAll(pageable);
    }

    @Override
    public Status getStatusById(String id) {
        return Status.valueOf(this.reportRepository.getStatusById(UUID.fromString(id)));
    }

    @Override
    public ResponseEntity<String> save(UUID uuid) throws IOException {
        String fileName = uuid + ".xlsx";
        Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            byte[] fileContent = Files.readAllBytes(filePath);
            String base64Encoded = Base64.getEncoder().encodeToString(fileContent);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            );

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(base64Encoded);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
