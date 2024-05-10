package ru.kosterror.forms.service.question;

import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.dto.question.newquesiton.NewQuestionDto;

public interface QuestionService {

    QuestionDto createQuestion(NewQuestionDto question);

}
