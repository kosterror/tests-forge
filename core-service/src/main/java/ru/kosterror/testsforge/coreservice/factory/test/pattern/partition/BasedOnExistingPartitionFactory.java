package ru.kosterror.testsforge.coreservice.factory.test.pattern.partition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition.CreateBasedOnExistingPartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.factory.test.pattern.block.BlockFactory;
import ru.kosterror.testsforge.coreservice.service.partition.PartitionService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BasedOnExistingPartitionFactory {

    private final PartitionService partitionService;
    private final BlockFactory blockFactory;

    public PartitionEntity buildPartitionFromDto(CreateBasedOnExistingPartitionDto partitionDto) {
        var partitionEntity = partitionService.getPartitionEntityById(partitionDto.getPartitionId());
        return buildPartitionFromEntity(partitionEntity);
    }

    private PartitionEntity buildPartitionFromEntity(PartitionEntity partitionEntity) {
        var newPartitionEntity = new PartitionEntity();

        newPartitionEntity.setName(partitionEntity.getName());
        newPartitionEntity.setDescription(partitionEntity.getDescription());

        buildAndAddBlocks(partitionEntity.getBlocks(), newPartitionEntity);

        return newPartitionEntity;
    }

    private void buildAndAddBlocks(List<BlockEntity> blockEntities, PartitionEntity partitionEntity) {
        var newBlockEntities = new ArrayList<BlockEntity>(blockEntities.size());

        for (int i = 0; i < blockEntities.size(); i++) {
            var blockEntity = blockFactory.buildFromEntity(blockEntities.get(i));
            blockEntity.setOrder(i);
            blockEntity.setPartition(partitionEntity);

            newBlockEntities.add(blockEntity);
        }

        partitionEntity.setBlocks(newBlockEntities);
    }

}
