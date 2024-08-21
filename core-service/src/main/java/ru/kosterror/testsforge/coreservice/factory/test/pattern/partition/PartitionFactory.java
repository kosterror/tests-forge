package ru.kosterror.testsforge.coreservice.factory.test.pattern.partition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition.CreateBasedOnExistingPartitionDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition.CreatePartitionDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition.NewPartitionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.PartitionEntity;

@Component
@RequiredArgsConstructor
public class PartitionFactory {

    private final NewPartitionFactory newPartitionFactory;
    private final BasedOnExistingPartitionFactory basedOnExistingPartitionFactory;

    public PartitionEntity buildPartitionEntity(CreatePartitionDto partitionDto) {
        return switch (partitionDto.getType()) {
            case NEW -> newPartitionFactory.buildPartitionFromDto((NewPartitionDto) partitionDto);
            case BASED_ON_EXISTING -> basedOnExistingPartitionFactory.buildPartitionFromDto(
                    (CreateBasedOnExistingPartitionDto) partitionDto
            );
        };
    }

}
