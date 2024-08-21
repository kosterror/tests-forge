package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;

import java.util.UUID;

public interface PartitionRepository extends JpaRepository<PartitionEntity, UUID> {
}
