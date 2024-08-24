package ru.kosterror.testsforge.coreservice.service.partition;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.PartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;

import java.util.UUID;

public interface PartitionService {

    PartitionEntity getPartitionEntityById(UUID id);

    PartitionDto getPartition(UUID id);

    PaginationResponse<PartitionDto> getPartitions(String partitionName, int page, int size);
}
