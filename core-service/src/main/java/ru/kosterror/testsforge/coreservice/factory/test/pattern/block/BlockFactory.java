package ru.kosterror.testsforge.coreservice.factory.test.pattern.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockBasedOnExistingDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.NewDynamicBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.NewStaticBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;

@Component
@RequiredArgsConstructor
public class BlockFactory {

    private final DynamicBlockFactory dynamicBlockFactory;
    private final StaticBlockFactory staticBlockFactory;
    private final BasedOnExistingBlockFactory basedOnExistingBlockFactory;

    public BlockEntity build(CreateBlockDto blockDto) {
        return switch (blockDto.getType()) {
            case DYNAMIC -> dynamicBlockFactory.buildFromDto((NewDynamicBlockDto) blockDto);
            case STATIC -> staticBlockFactory.buildFromDto((NewStaticBlockDto) blockDto);
            case BASED_ON_EXISTING -> basedOnExistingBlockFactory.build((CreateBlockBasedOnExistingDto) blockDto);
        };
    }

}
