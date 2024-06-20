package ru.kosterror.testsforge.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.userservice.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

}
