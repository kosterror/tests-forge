package ru.kosterror.forms.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.coreservice.entity.question.QuestionEntity;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
}
