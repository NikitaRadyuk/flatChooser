package by.itacademy.audit.controller;

import by.itacademy.audit.core.dto.AuditDTO;
import by.itacademy.audit.core.dto.UserAuditDTO;
import by.itacademy.audit.core.entity.AuditEntity;
import by.itacademy.audit.service.api.IAuditService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditRestController{
    private final IAuditService service;
    private final ModelMapper modelMapper;

    public AuditRestController(IAuditService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    @ResponseBody
    public Page<AuditDTO> getAudit(@RequestParam(defaultValue =  "0") Integer page,
                                   @RequestParam(defaultValue = "20") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AuditEntity> audit = this.service.getAudit(pageable);
        return audit.map(AuditRestController::apply);
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    public AuditDTO getAuditById(@PathVariable UUID uuid) {
        Optional<AuditEntity> auditById = this.service.getAuditById(uuid);
        return modelMapper.map(auditById, AuditDTO.class);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuditDTO acceptReq(@RequestHeader("Authorization") String AUTHORIZATION,
                              @RequestBody AuditDTO auditDTO){
        AuditEntity saveAudit = this.service.saveAudit(auditDTO);
        return modelMapper.map(saveAudit, AuditDTO.class);
    }

    private static AuditDTO apply(AuditEntity audit) {
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setUuid(audit.getUuid());
        auditDTO.setDtCreate(audit.getDtCreate());
        auditDTO.setUserAuditDTO(new UserAuditDTO(audit.getUuid(), audit.getMail(), audit.getFio(), audit.getRole()));
        auditDTO.setAction(audit.getText());
        auditDTO.setType(audit.getEssenceType());
        auditDTO.setTypeId(audit.getEssenceId());
        return auditDTO;
    }
}
