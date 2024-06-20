package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kosterror.testsforge.coreservice.entity.form.FormPatternEntity;

import java.util.UUID;

public interface FormPatternRepository extends
        JpaRepository<FormPatternEntity, UUID>,
        JpaSpecificationExecutor<FormPatternEntity> {
}
