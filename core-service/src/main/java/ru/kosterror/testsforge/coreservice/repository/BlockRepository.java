package ru.kosterror.testsforge.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;

import java.util.UUID;

public interface BlockRepository extends JpaRepository<BlockEntity, UUID> {
}
