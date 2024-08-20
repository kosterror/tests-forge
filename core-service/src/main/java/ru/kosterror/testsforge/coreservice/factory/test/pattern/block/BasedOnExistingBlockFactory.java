package ru.kosterror.testsforge.coreservice.factory.test.pattern.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockBasedOnExistingDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.StaticBlockEntity;
import ru.kosterror.testsforge.coreservice.service.block.BlockService;

@Component
@RequiredArgsConstructor
public class BasedOnExistingBlockFactory {

    private final StaticBlockFactory staticBlockFactory;
    private final DynamicBlockFactory dynamicBlockFactory;
    private final BlockService blockService;

    public BlockEntity build(CreateBlockBasedOnExistingDto blockDto) {
        var blockEntity = blockService.getBlockEntityById(blockDto.getBlockId());

        return switch (blockEntity.getType()) {
            case STATIC -> staticBlockFactory.buildFromEntity((StaticBlockEntity) blockEntity);
            case DYNAMIC -> dynamicBlockFactory.buildFromEntity((DynamicBlockEntity) blockEntity);
        };
    }

}
