package ru.kosterror.testsforge.coreservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GeneratedTestFactory {

    private final PartitionFactory partitionFactory;

    public GeneratedTestEntity buildGeneratedTestEntity(PublishedTestEntity publishedTest, UUID userId) {
        return GeneratedTestEntity.builder()
                .status(GeneratedTestStatus.CREATED)
                .publishedTest(publishedTest)
                .userId(userId)
                .partitions(partitionFactory.buildPartitions(publishedTest.getTestPattern().getPartitions()))
                .build();
    }
}
