package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.PartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.mapper.block.BlockMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = BlockMapper.class
)
public interface PartitionMapper {

    PartitionDto toDto(PartitionEntity partitionEntity);

}
