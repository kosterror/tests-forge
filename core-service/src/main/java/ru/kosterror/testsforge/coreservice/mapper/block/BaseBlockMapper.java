package ru.kosterror.testsforge.coreservice.mapper.block;

import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;

public abstract class BaseBlockMapper {

    protected void mapBaseBlockDtoFields(BlockDto dto, BlockEntity entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
    }

    public abstract BlockDto toDto(BlockEntity baseEntity);
}
