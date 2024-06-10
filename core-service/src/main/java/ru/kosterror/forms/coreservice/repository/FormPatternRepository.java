package ru.kosterror.forms.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.coreservice.entity.form.FormPatternEntity;

import java.util.UUID;

public interface FormPatternRepository extends JpaRepository<FormPatternEntity, UUID> {
}
