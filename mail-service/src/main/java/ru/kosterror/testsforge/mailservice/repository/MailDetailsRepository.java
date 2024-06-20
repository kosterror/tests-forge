package ru.kosterror.testsforge.mailservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kosterror.testsforge.mailservice.entity.MailDetailsEntity;

import java.util.UUID;

public interface MailDetailsRepository extends MongoRepository<MailDetailsEntity, UUID> {
}
