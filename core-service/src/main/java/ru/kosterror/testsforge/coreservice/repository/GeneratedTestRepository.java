package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;

import java.util.Optional;
import java.util.UUID;

public interface GeneratedTestRepository extends
        JpaRepository<GeneratedTestEntity, UUID>,
        JpaSpecificationExecutor<GeneratedTestEntity> {

    Optional<GeneratedTestEntity> findByPublishedTestIdAndUserId(UUID publishedTestId, UUID userId);

}
