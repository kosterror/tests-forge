package ru.kosterror.testsforge.coreservice.factory.test.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationQuestion;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationVariant;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Variant;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VerificationVariantFactory {

    private final VerificationQuestionFactory verificationQuestionFactory;

    public VerificationVariant build(Variant variant, Map<UUID, QuestionDto> questionsWithCorrectAnswer) {
        List<VerificationQuestion> questions = verificationQuestionFactory.build(
                variant.getQuestions(),
                questionsWithCorrectAnswer
        );

        return new VerificationVariant(
                variant.getId(),
                variant.getName(),
                variant.getDescription(),
                questions
        );
    }

}
