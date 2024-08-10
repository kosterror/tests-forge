package ru.kosterror.testsforge.coreservice.service.processor;

import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;

import java.util.List;

public interface QuestionProcessor {

    void process(List<Question> questions, AnswersDto answers);

}
