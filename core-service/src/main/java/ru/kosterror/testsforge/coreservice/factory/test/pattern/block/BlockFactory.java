package ru.kosterror.testsforge.coreservice.factory.test.pattern.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockBasedOnExistingDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.NewDynamicBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.NewStaticBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.StaticBlockEntity;

@Component
@RequiredArgsConstructor
public class BlockFactory {

    private final DynamicBlockFactory dynamicBlockFactory;
    private final StaticBlockFactory staticBlockFactory;
    private final BasedOnExistingBlockFactory basedOnExistingBlockFactory;

    public BlockEntity buildFromDto(CreateBlockDto blockDto) {
        return switch (blockDto.getType()) {
            case DYNAMIC -> dynamicBlockFactory.buildFromDto((NewDynamicBlockDto) blockDto);
            case STATIC -> staticBlockFactory.buildFromDto((NewStaticBlockDto) blockDto);
            case BASED_ON_EXISTING -> basedOnExistingBlockFactory.build((CreateBlockBasedOnExistingDto) blockDto);
        };
    }

    public BlockEntity buildFromEntity(BlockEntity blockEntity) {
        return switch (blockEntity.getType()) {
            case DYNAMIC -> dynamicBlockFactory.buildFromEntity((DynamicBlockEntity) blockEntity);
            case STATIC -> staticBlockFactory.buildFromEntity((StaticBlockEntity) blockEntity);
        };
    }

}
