package ru.kosterror.forms.coreservice.service;

import ru.kosterror.forms.coreservice.dto.question.createupdate.CreateUpdateQuestionDto;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;

import java.util.UUID;

public interface QuestionService {

    QuestionDto createQuestion(UUID userId, CreateUpdateQuestionDto question);

    QuestionDto getQuestion(UUID id);
}
