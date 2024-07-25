package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.coreservice.entity.subject.SubjectEntity;

import java.util.List;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<SubjectEntity, UUID> {

    List<SubjectEntity> findAllByNameContainingIgnoreCaseOrderByName(String name);

}
