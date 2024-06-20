package ru.kosterror.testsforge.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.userservice.entity.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByToken(String token);

}
