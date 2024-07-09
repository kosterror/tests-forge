package ru.kosterror.testsforge.coreservice.mapper.block;

import ru.kosterror.testsforge.coreservice.dto.test.pattern.BlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdateBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.BlockEntity;

public abstract class BaseBlockMapper {

    public abstract BlockEntity toEntity(UpdateBlockDto dto);

    protected void mapBaseBlockEntityFields(BlockEntity entity, UpdateBlockDto dto) {
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
