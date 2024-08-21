package ru.kosterror.testsforge.coreservice.factory.test.pattern.partition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition.NewPartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.factory.test.pattern.block.BlockFactory;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewPartitionFactory {

    private final BlockFactory blockFactory;

    public PartitionEntity buildPartitionFromDto(NewPartitionDto partitionDto) {
        var partitionEntity = new PartitionEntity();

        partitionEntity.setName(partitionDto.getName());
        partitionEntity.setDescription(partitionDto.getDescription());

        buildAndAddBlocks(partitionDto.getBlocks(), partitionEntity);

        return partitionEntity;
    }

    private void buildAndAddBlocks(List<CreateBlockDto> blockDtos, PartitionEntity partitionEntity) {
        var blockEntities = new ArrayList<BlockEntity>(blockDtos.size());

        for (int i = 0; i < blockDtos.size(); i++) {
            var blockEntity = blockFactory.buildFromDto(blockDtos.get(i));
            blockEntity.setOrder(i);
            blockEntity.setPartition(partitionEntity);

            blockEntities.add(blockEntity);
        }

        partitionEntity.setBlocks(blockEntities);
    }

}
