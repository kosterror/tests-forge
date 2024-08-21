package ru.kosterror.testsforge.coreservice.service.partition.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.repository.PartitionRepository;
import ru.kosterror.testsforge.coreservice.service.partition.PartitionService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartitionServiceImpl implements PartitionService {

    private final PartitionRepository partitionRepository;

    @Override
    public PartitionEntity getPartitionEntityById(UUID id) {
        return partitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Partition with id %s not found".formatted(id)));
    }
}
