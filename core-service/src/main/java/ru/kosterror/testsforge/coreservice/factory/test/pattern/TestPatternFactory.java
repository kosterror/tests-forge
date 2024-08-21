package ru.kosterror.testsforge.coreservice.factory.test.pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.NewTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition.CreatePartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.factory.test.pattern.partition.PartitionFactory;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestPatternFactory {

    private final PartitionFactory partitionFactory;

    public TestPatternEntity buildTestPatternEntity(NewTestPatternDto testPatternDto) {
        var testPatternEntity = new TestPatternEntity();
        testPatternEntity.setName(testPatternDto.name());
        testPatternEntity.setDescription(testPatternDto.description());

        buildAndAddPartitions(testPatternDto.partitions(), testPatternEntity);

        return testPatternEntity;
    }

    private void buildAndAddPartitions(List<CreatePartitionDto> partitionDtos,
                                       TestPatternEntity testPatternEntity
    ) {
        var partitionEntities = new ArrayList<PartitionEntity>(partitionDtos.size());

        for (int i = 0; i < partitionDtos.size(); i++) {
            var partitionEntity = partitionFactory.buildPartitionEntity(partitionDtos.get(i));
            partitionEntity.setOrder(i);
            partitionEntity.setTest(testPatternEntity);

            partitionEntities.add(partitionEntity);
        }

        testPatternEntity.setPartitions(partitionEntities);
    }

}
