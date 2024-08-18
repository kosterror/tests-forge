package ru.kosterror.testsforge.coreservice.factory.test.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationGeneratedTest;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.service.util.TestUtilService;

@Component
@RequiredArgsConstructor
public class VerificationGeneratedTestFactory {

    private final VerificationPartitionFactory verificationPartitionFactory;
    private final TestUtilService testUtilService;

    public VerificationGeneratedTest build(GeneratedTestEntity generatedTest) {
        var testPattern = generatedTest.getPublishedTest().getTestPattern();

        var questionsWithCorrectAnswers = testUtilService.extractQuestionDtoMap(
                testPattern
        );

        var partitions = verificationPartitionFactory.build(generatedTest.getPartitions(), questionsWithCorrectAnswers);

        return new VerificationGeneratedTest(
                generatedTest.getId(),
                generatedTest.getStatus(),
                generatedTest.getUserId(),
                partitions
        );
    }

}
