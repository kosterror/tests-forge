package ru.kosterror.testsforge.coreservice.factory.test.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationPartition;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Partition;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class VerificationPartitionFactory {

    private final VerificationBlockFactory verificationBlockFactory;

    public List<VerificationPartition> build(List<Partition> partitions,
                                             Map<UUID, QuestionDto> questionsWithCorrectAnswer
    ) {
        return Stream.ofNullable(partitions)
                .flatMap(List::stream)
                .map(partition -> build(partition, questionsWithCorrectAnswer))
                .toList();
    }

    private VerificationPartition build(Partition partition,
                                        Map<UUID, QuestionDto> questionsWithCorrectAnswer
    ) {

        var verificationBlocks = verificationBlockFactory.build(partition.getBlocks(), questionsWithCorrectAnswer);

        return new VerificationPartition(
                partition.getId(),
                partition.getName(),
                partition.getDescription(),
                verificationBlocks
        );
    }
}
