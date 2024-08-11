package ru.kosterror.testsforge.coreservice.service.processor.question;

import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;

import java.util.List;

public interface QuestionProcessor {

    void markQuestionAnswers(List<Question> questions, AnswersDto answersDto);

}
