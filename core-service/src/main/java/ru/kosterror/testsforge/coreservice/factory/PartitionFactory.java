package ru.kosterror.testsforge.coreservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Partition;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PartitionFactory {

    private final BlockFactory blockFactory;

    public List<Partition> buildPartitions(List<PartitionEntity> partitionEntities) {
        return partitionEntities.stream()
                .map(this::buildPartition)
                .toList();
    }

    public Partition buildPartition(PartitionEntity partitionEntity) {
        return new Partition(
                partitionEntity.getId(),
                partitionEntity.getName(),
                partitionEntity.getDescription(),
                blockFactory.buildBlocks(partitionEntity.getBlocks())
        );
    }

}
