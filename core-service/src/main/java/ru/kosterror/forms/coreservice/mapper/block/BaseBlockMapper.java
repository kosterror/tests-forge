package ru.kosterror.forms.coreservice.mapper.block;

import ru.kosterror.forms.coreservice.dto.formpattern.createupdate.CreateBlockDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.BlockDto;
import ru.kosterror.forms.coreservice.entity.form.BlockEntity;

public abstract class BaseBlockMapper {

    public abstract BlockEntity toEntity(CreateBlockDto dto);

    protected void mapBaseBlockEntityFields(BlockEntity entity, CreateBlockDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
    }

    protected void mapBaseBlockDtoFields(BlockDto dto, BlockEntity entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
    }

    public abstract BlockDto toDto(BlockEntity baseEntity);
}
