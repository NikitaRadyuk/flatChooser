package by.itacademy.user.aspect;

import by.itacademy.user.clients.AuditFeignClient;
import by.itacademy.user.controller.utils.JwtTokenHandler;
import by.itacademy.user.core.dto.*;
import by.itacademy.user.core.enums.UserRole;
import by.itacademy.user.core.dto.interfaces.Mailable;
import by.itacademy.user.core.dto.interfaces.Userable;
import by.itacademy.user.core.entity.UserEntity;
import by.itacademy.user.exceptions.ValidationException;
import by.itacademy.user.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class AuditedAspect {
    private final UserRepository userRepository;
    private final AuditFeignClient auditFeignClient;
    private final JwtTokenHandler jwtTokenHandler;

    public AuditedAspect(UserRepository userRepository, AuditFeignClient auditFeignClient, JwtTokenHandler jwtTokenHandler) {
        this.userRepository = userRepository;
        this.auditFeignClient = auditFeignClient;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Around("@annotation(Auditor)")
    public Object checkActivation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Auditor annotation = signature.getMethod().getAnnotation(Auditor.class);
        Object result = joinPoint.proceed();
        AuditDTO auditDTO = buildAudit(joinPoint, annotation, result);
        String token = "" + jwtTokenHandler.generateAccessToken(new UserDetailsDTO().setRole(UserRole.ADMIN));
        auditFeignClient.sendRequestToCreateLog(token, auditDTO);
        return result;
    }

    private AuditDTO buildAudit(ProceedingJoinPoint joinPoint, Auditor annotation, Object result){
        switch(annotation.action()){
            case LOGIN,VERIFICATION -> {
                return getAuditDTOByMail(joinPoint, annotation);
            }
            case REGISTRATION -> {
                return createAuditDTO(annotation, (UserEntity) result);
            }
            case UPDATE_USER, SAVE_USER -> {
                return getAuditDTOForUser(annotation, result);
            }
            case INFO_ABOUT_ALL_USERS -> {
                return getAuditDTOForInfoAboutAllUsers(annotation);
            }
            case INFO_ABOUT_USER_BY_ID, INFO_ABOUT_ME -> {
                return getAuditDtoForUserInfo(annotation, result);
            }
            default -> throw new RuntimeException("Strange exception in building Audit. The action was: " + annotation.action());
        }
    }

    private AuditDTO getAuditDTOByMail(ProceedingJoinPoint joinPoint, Auditor annotation){
        Mailable dto = (Mailable) Arrays.stream(joinPoint.getArgs()).toList().get(0);
        return createAuditDTO(annotation, findByMail(dto.getMail()));
    }

    private AuditDTO getAuditDtoForUserInfo(Auditor annotation, Object result){
        return createAuditDTO(annotation, getUserDetailFromSecurityContext(),((UserEntity) result).getUuid());
    }

    private UserDetailsDTO getUserDetailFromSecurityContext(){
        return (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private UserEntity findByMail(String mail){
        return userRepository.findByMail(mail).orElseThrow(ValidationException::new);
    }

    private AuditDTO createAuditDTO(Auditor annotation, Userable userable){
        return new AuditDTO().setUserAuditDTO(buildUserAudit(userable))
                .setActionForAudit(annotation.action())
                .setEntityForAudit(annotation.entity())
                .setId(userable.getUuid().toString());
    }

    private AuditDTO getAuditDTOForUser(Auditor annotation, Object result){
        /*var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Userable) {
            return createAuditDTO(annotation, (Userable) principal, ((UserEntity) result).getUuid());
        } else {
            return null;
        }*/
        return createAuditDTO(annotation, getUserDetailFromSecurityContext(), ((UserEntity) result).getUuid());
    }

    private AuditDTO createAuditDTO(Auditor annotation, Userable userable, UUID uuid) {
        return new AuditDTO().setUserAuditDTO(buildUserAudit(userable))
                .setActionForAudit(annotation.action())
                .setEntityForAudit(annotation.entity())
                .setId(uuid.toString());
    }

    private AuditDTO getAuditDTOForInfoAboutAllUsers(Auditor annotation){
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return createAuditDTO(annotation, userDetailsDTO, userDetailsDTO.getUuid());
    }


    private UserAuditDTO buildUserAudit(Userable userable){
        return new UserAuditDTO().setUuid(userable.getUuid())
                .setFio(userable.getFio())
                .setRole(userable.getRole())
                .setMail(userable.getMail());
    }

}
