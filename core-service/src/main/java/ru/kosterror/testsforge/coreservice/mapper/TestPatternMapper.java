package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.TestPatternDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = PartitionMapper.class
)
public interface TestPatternMapper {

    TestPatternDto toDto(TestPatternEntity testPatternEntity);

    BaseTestPatternDto toBaseDto(TestPatternEntity testPatternEntity);

}
