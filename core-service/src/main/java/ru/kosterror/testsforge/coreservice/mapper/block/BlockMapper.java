package ru.kosterror.testsforge.coreservice.mapper.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;

@Component
@RequiredArgsConstructor
public class BlockMapper {

    private final DynamicBlockMapper dynamicBlockMapper;
    private final StaticBlockMapper staticBlockMapper;

    public BlockDto toDto(BlockEntity entity) {
        return switch (entity.getType()) {
            case DYNAMIC -> dynamicBlockMapper.toDto(entity);
            case STATIC -> staticBlockMapper.toDto(entity);
        };
    }

}
