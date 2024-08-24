package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;

import java.util.UUID;

public interface PartitionRepository extends
        JpaRepository<PartitionEntity, UUID>,
        JpaSpecificationExecutor<PartitionEntity> {

}
