package ru.kosterror.forms.mailservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kosterror.forms.mailservice.entity.MailDetailsEntity;

import java.util.UUID;

public interface MailDetailsRepository extends MongoRepository<MailDetailsEntity, UUID> {
}
