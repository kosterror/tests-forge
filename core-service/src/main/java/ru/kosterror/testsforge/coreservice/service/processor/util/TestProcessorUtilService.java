package ru.kosterror.testsforge.coreservice.service.processor.util;

import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;
import java.util.UUID;

public interface TestProcessorUtilService<T extends Question> {

    T findQuestion(List<T> questions, UUID questionId);

    List<T> filterQuestions(List<Question> questions, QuestionType questionType, Class<T> classToCast);
}
