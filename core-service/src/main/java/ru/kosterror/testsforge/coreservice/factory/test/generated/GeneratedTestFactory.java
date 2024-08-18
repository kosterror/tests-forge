package ru.kosterror.testsforge.coreservice.factory.test.generated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GeneratedTestFactory {

    private final GeneratedPartitionFactory partitionFactory;

    public GeneratedTestEntity buildGeneratedTestEntity(PublishedTestEntity publishedTest, UUID userId) {
        return GeneratedTestEntity.builder()
                .status(TestStatus.NEW)
                .publishedTest(publishedTest)
                .userId(userId)
                .partitions(partitionFactory.buildPartitions(publishedTest.getTestPattern().getPartitions()))
                .build();
    }
}
