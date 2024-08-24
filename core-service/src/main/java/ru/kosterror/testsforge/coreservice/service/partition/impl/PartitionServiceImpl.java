package ru.kosterror.testsforge.coreservice.service.partition.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.PartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity_;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.PartitionMapper;
import ru.kosterror.testsforge.coreservice.repository.PartitionRepository;
import ru.kosterror.testsforge.coreservice.service.partition.PartitionService;
import ru.kosterror.testsforge.coreservice.specificaiton.PartitionSpecification;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartitionServiceImpl implements PartitionService {

    private final PartitionRepository partitionRepository;
    private final PartitionMapper partitionMapper;

    @Override
    public PartitionEntity getPartitionEntityById(UUID id) {
        return partitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Partition with id %s not found".formatted(id)));
    }

    @Override
    public PartitionDto getPartition(UUID id) {
        return partitionMapper.toDto(getPartitionEntityById(id));
    }

    @Override
    public PaginationResponse<PartitionDto> getPartitions(String partitionName, int page, int size) {
        var specification = Specification.<PartitionEntity>where(null)
                .and(PartitionSpecification.hasNameLike(partitionName));

        var pageable = PageRequest.of(page, size, Sort.Direction.ASC, PartitionEntity_.NAME);

        var partitions = partitionRepository.findAll(specification, pageable)
                .stream()
                .map(partitionMapper::toDto)
                .toList();

        return new PaginationResponse<>(page, size, partitions);
    }
}
