package ru.kosterror.forms.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.forms.coreservice.dto.form.createupdate.CreateFormDto;
import ru.kosterror.forms.coreservice.dto.form.full.FormDto;
import ru.kosterror.forms.coreservice.entity.form.FormEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = PartitionMapper.class
)
public abstract class FormMapper {

    public abstract FormEntity toEntity(CreateFormDto formDto);

    public abstract FormDto toDto(FormEntity formEntity);

    @AfterMapping
    void orderPartitions(@MappingTarget FormEntity formEntity) {
        if (formEntity.getPartitions() != null) {
            for (int order = 0; order < formEntity.getPartitions().size(); order++) {
                formEntity.getPartitions().get(order).setOrder(order);
            }
        }
    }

    @AfterMapping
    void addFormToPartition(@MappingTarget FormEntity formEntity) {
        if (formEntity.getPartitions() != null) {
            for (var partition : formEntity.getPartitions()) {
                partition.setForm(formEntity);
            }
        }
    }

}
