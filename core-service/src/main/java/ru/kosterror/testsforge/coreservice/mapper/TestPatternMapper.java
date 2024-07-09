package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.testsforge.coreservice.dto.testpattern.full.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.testpattern.full.TestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.testpattern.update.UpdateTestPatternDto;
import ru.kosterror.testsforge.coreservice.entity.test.TestPatternEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = PartitionMapper.class
)
public abstract class TestPatternMapper {

    public abstract TestPatternEntity toEntity(UpdateTestPatternDto formDto);

    public abstract TestPatternDto toDto(TestPatternEntity testPatternEntity);

    public abstract BaseTestPatternDto toBaseDto(TestPatternEntity testPatternEntity);

    @AfterMapping
    void orderPartitions(@MappingTarget TestPatternEntity testPatternEntity) {
        if (testPatternEntity.getPartitions() != null) {
            for (int order = 0; order < testPatternEntity.getPartitions().size(); order++) {
                testPatternEntity.getPartitions().get(order).setOrder(order);
            }
        }
    }

    @AfterMapping
    void addFormToPartition(@MappingTarget TestPatternEntity testPatternEntity) {
        if (testPatternEntity.getPartitions() != null) {
            for (var partition : testPatternEntity.getPartitions()) {
                partition.setTest(testPatternEntity);
            }
        }
    }

}
