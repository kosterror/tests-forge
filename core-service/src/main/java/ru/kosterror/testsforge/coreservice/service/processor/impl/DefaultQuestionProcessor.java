package ru.kosterror.testsforge.coreservice.service.processor.impl;

import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.service.processor.QuestionProcessor;

import java.util.List;
import java.util.UUID;

public abstract class DefaultQuestionProcessor<T extends Question> implements QuestionProcessor {

    protected T findQuestion(List<T> questions, UUID questionId) {
        return questions.stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Question with id %s not found".formatted(questionId)));
    }

    protected List<T> filterQuestions(List<Question> questions,
                                      QuestionType questionType,
                                      Class<T> classToCast
    ) {
        return questions.stream()
                .filter(question -> questionType.equals(question.getType()))
                .map(classToCast::cast)
                .toList();
    }


}

