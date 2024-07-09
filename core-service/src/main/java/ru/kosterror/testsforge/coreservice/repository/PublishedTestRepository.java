package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.coreservice.entity.test.PublishedTestEntity;

import java.util.UUID;

public interface PublishedTestRepository extends JpaRepository<PublishedTestEntity, UUID> {
}
