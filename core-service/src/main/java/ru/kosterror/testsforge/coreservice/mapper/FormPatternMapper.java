package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.testsforge.coreservice.dto.formpattern.full.BaseFormPatternDto;
import ru.kosterror.testsforge.coreservice.dto.formpattern.full.FormPatternDto;
import ru.kosterror.testsforge.coreservice.dto.formpattern.update.UpdateFormPatternDto;
import ru.kosterror.testsforge.coreservice.entity.form.FormPatternEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = PartitionMapper.class
)
public abstract class FormPatternMapper {

    public abstract FormPatternEntity toEntity(UpdateFormPatternDto formDto);

    public abstract FormPatternDto toDto(FormPatternEntity formPatternEntity);

    public abstract BaseFormPatternDto toBaseDto(FormPatternEntity formPatternEntity);

    @AfterMapping
    void orderPartitions(@MappingTarget FormPatternEntity formPatternEntity) {
        if (formPatternEntity.getPartitions() != null) {
            for (int order = 0; order < formPatternEntity.getPartitions().size(); order++) {
                formPatternEntity.getPartitions().get(order).setOrder(order);
            }
        }
    }

    @AfterMapping
    void addFormToPartition(@MappingTarget FormPatternEntity formPatternEntity) {
        if (formPatternEntity.getPartitions() != null) {
            for (var partition : formPatternEntity.getPartitions()) {
                partition.setForm(formPatternEntity);
            }
        }
    }

}
