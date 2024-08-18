package ru.kosterror.testsforge.coreservice.factory.test.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.exception.InternalException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class VerificationQuestionFactory {

    public List<VerificationQuestion> build(List<Question> questions,
                                            Map<UUID, QuestionDto> questionsWithCorrectAnswer
    ) {
        return Stream.ofNullable(questions)
                .flatMap(List::stream)
                .map(question -> build(question, questionsWithCorrectAnswer))
                .toList();
    }

    private VerificationQuestion build(Question question,
                                       Map<UUID, QuestionDto> questionsWithCorrectAnswer) {
        return new VerificationQuestion(
                getCorrectAnswer(question.getId(), questionsWithCorrectAnswer),
                question
        );
    }

    private QuestionDto getCorrectAnswer(UUID questionId, Map<UUID, QuestionDto> questionsWithCorrectAnswer) {
        if (questionsWithCorrectAnswer.containsKey(questionId)) {
            return questionsWithCorrectAnswer.get(questionId);
        }

        throw new InternalException(
                "Question with id %s not found in questionsWithCorrectAnswer".formatted(questionId)
        );
    }

}
