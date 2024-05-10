package ru.kosterror.forms.service.question;

import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.dto.question.newquesiton.NewQuestionDto;

import java.util.UUID;

public interface QuestionService {

    QuestionDto createQuestion(NewQuestionDto question);

    QuestionDto getQuestion(UUID id);
}
