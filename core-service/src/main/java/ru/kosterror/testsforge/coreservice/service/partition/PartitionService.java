package ru.kosterror.testsforge.coreservice.service.partition;

import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;

import java.util.UUID;

public interface PartitionService {

    PartitionEntity getPartitionEntityById(UUID id);

}
