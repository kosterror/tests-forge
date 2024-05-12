package ru.kosterror.forms.coreservice.service.question;

import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.forms.coreservice.dto.question.newquesiton.NewQuestionDto;

import java.util.UUID;

public interface QuestionService {

    QuestionDto createQuestion(NewQuestionDto question);

    QuestionDto getQuestion(UUID id);
}
