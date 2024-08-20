package ru.kosterror.testsforge.coreservice.factory.test.pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition.UpdatePartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.factory.test.pattern.block.BlockFactory;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PartitionFactory {

    private final BlockFactory blockFactory;

    public PartitionEntity buildPartitionEntity(UpdatePartitionDto partitionDto) {
        var partitionEntity = new PartitionEntity();

        partitionEntity.setName(partitionDto.name());
        partitionEntity.setDescription(partitionDto.description());

        buildAndAddBlocks(partitionDto.blocks(), partitionEntity);

        return partitionEntity;
    }

    private void buildAndAddBlocks(List<CreateBlockDto> blockDtos, PartitionEntity partitionEntity) {
        var blockEntities = new ArrayList<BlockEntity>(blockDtos.size());

        for (int i = 0; i < blockDtos.size(); i++) {
            var blockEntity = blockFactory.build(blockDtos.get(i));
            blockEntity.setOrder(i);
            blockEntity.setPartition(partitionEntity);

            blockEntities.add(blockEntity);
        }

        partitionEntity.setBlocks(blockEntities);
    }

}
