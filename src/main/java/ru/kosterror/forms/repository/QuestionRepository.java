package ru.kosterror.forms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.entity.question.QuestionEntity;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
}
