package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.PartitionDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdatePartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.mapper.block.BlockMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = BlockMapper.class
)
public abstract class PartitionMapper {

    public abstract PartitionEntity toEntity(UpdatePartitionDto partitionDto);

    public abstract PartitionDto toDto(PartitionEntity partitionEntity);

    @AfterMapping
    void orderBlocks(@MappingTarget PartitionEntity partitionEntity) {
        if (partitionEntity.getBlocks() != null) {
            for (int order = 0; order < partitionEntity.getBlocks().size(); order++) {
                partitionEntity.getBlocks().get(order).setOrder(order);
            }
        }
    }

    @AfterMapping
    void addPartitionToBlock(@MappingTarget PartitionEntity partitionEntity) {
        if (partitionEntity.getBlocks() != null) {
            for (var block : partitionEntity.getBlocks()) {
                block.setPartition(partitionEntity);
            }
        }
    }

}
