package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;

import java.util.UUID;

public interface QuestionRepository extends
        JpaRepository<QuestionEntity, UUID>,
        JpaSpecificationExecutor<QuestionEntity> {
}
