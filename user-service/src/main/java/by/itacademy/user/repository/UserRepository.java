package by.itacademy.user.repository;

import by.itacademy.user.core.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    Optional<UserEntity> findById(UUID uuid);
    Optional<UserEntity> findByMail(String mail);
    Page<UserEntity> findAll(Pageable pageable);
    Boolean existsByMail(String mail);

}
