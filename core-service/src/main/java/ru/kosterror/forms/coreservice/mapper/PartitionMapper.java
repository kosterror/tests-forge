package ru.kosterror.forms.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.forms.coreservice.dto.formpattern.full.PartitionDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdatePartitionDto;
import ru.kosterror.forms.coreservice.entity.form.PartitionEntity;
import ru.kosterror.forms.coreservice.mapper.block.BlockMapper;

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
