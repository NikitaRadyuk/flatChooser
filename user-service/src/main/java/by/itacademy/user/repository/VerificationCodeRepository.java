package by.itacademy.user.repository;

import by.itacademy.user.core.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationEntity, UUID> {
    @Query("SELECT v.mail FROM VerificationEntity as v WHERE v.token = :token")
    String findMailByToken(String token);
}
