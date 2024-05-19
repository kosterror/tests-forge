package ru.kosterror.forms.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.userservice.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

}
