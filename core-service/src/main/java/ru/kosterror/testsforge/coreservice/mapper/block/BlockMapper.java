package ru.kosterror.testsforge.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.testpattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.dto.testpattern.update.UpdateBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.BlockEntity;

@Component
@RequiredArgsConstructor
public class BlockMapper {

    private final DynamicBlockMapper dynamicBlockMapper;
    private final StaticBlockMapper staticBlockMapper;

    public BlockEntity toEntity(UpdateBlockDto dto) {
        return switch (dto.getType()) {
            case DYNAMIC -> dynamicBlockMapper.toEntity(dto);
            case STATIC -> staticBlockMapper.toEntity(dto);
        };
    }

    public BlockDto toDto(BlockEntity entity) {
        return switch (entity.getType()) {
            case DYNAMIC -> dynamicBlockMapper.toDto(entity);
            case STATIC -> staticBlockMapper.toDto(entity);
        };
    }

}
