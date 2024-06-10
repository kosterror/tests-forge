package ru.kosterror.forms.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.coreservice.entity.form.FormEntity;

import java.util.UUID;

public interface FormRepository extends JpaRepository<FormEntity, UUID> {
}
