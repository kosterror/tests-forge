package ru.kosterror.testsforge.coreservice.service.impl.factory;

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
        return new GeneratedTestEntity(
                GeneratedTestStatus.CREATED,
                publishedTest,
                userId,
                partitionFactory.buildPartitions(publishedTest.getTestPattern().getPartitions())
        );
    }
}
