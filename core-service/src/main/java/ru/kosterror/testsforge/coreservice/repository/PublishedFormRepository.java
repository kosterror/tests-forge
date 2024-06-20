package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.coreservice.entity.form.PublishedFormEntity;

import java.util.UUID;

public interface PublishedFormRepository extends JpaRepository<PublishedFormEntity, UUID> {
}
