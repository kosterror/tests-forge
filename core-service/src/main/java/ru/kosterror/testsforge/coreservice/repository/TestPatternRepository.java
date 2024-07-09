package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kosterror.testsforge.coreservice.entity.test.TestPatternEntity;

import java.util.UUID;

public interface TestPatternRepository extends
        JpaRepository<TestPatternEntity, UUID>,
        JpaSpecificationExecutor<TestPatternEntity> {
}
