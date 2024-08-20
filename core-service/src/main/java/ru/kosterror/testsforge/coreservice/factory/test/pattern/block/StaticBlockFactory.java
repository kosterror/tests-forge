package ru.kosterror.testsforge.coreservice.factory.test.pattern.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.NewStaticBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.StaticBlockEntity;
import ru.kosterror.testsforge.coreservice.factory.test.pattern.VariantFactory;

@Component
@RequiredArgsConstructor
public class StaticBlockFactory {

    private final VariantFactory variantFactory;

    public StaticBlockEntity buildFromDto(NewStaticBlockDto blockDto) {
        var blockEntity = new StaticBlockEntity();
        var variants = variantFactory.buildVariantEntitiesFromDtos(blockDto.getVariants(), blockEntity);

        blockEntity.setName(blockDto.getName());
        blockEntity.setDescription(blockDto.getDescription());
        blockEntity.setType(BlockType.STATIC);
        blockEntity.setVariants(variants);

        return blockEntity;
    }

    public StaticBlockEntity buildFromEntity(StaticBlockEntity blockEntity) {
        var newBlockEntity = new StaticBlockEntity();
        var newVariants = variantFactory.buildVariantEntitiesFromEntities(blockEntity.getVariants(), newBlockEntity);

        newBlockEntity.setName(blockEntity.getName());
        newBlockEntity.setDescription(blockEntity.getDescription());
        newBlockEntity.setType(BlockType.STATIC);
        newBlockEntity.setVariants(newVariants);

        return newBlockEntity;
    }

}
