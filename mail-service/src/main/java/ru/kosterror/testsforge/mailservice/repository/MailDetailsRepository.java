package ru.kosterror.testsforge.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.mailservice.entity.MailDetailsEntity;

import java.util.UUID;

public interface MailDetailsRepository extends JpaRepository<MailDetailsEntity, UUID> {
}
