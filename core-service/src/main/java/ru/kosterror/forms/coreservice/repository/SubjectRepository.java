package ru.kosterror.forms.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.forms.coreservice.entity.subject.SubjectEntity;

import java.util.List;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<SubjectEntity, UUID> {

    List<SubjectEntity> findAllByNameContaining(String name);

}
