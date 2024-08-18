package ru.kosterror.testsforge.coreservice.factory.test.generated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Partition;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeneratedPartitionFactory {

    private final GeneratedBlockFactory generatedBlockFactory;

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
                generatedBlockFactory.buildBlocks(partitionEntity.getBlocks())
        );
    }

}
