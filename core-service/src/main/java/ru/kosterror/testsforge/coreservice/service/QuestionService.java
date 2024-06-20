package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.update.UpdateQuestionDto;

import java.util.UUID;

public interface QuestionService {

    QuestionDto createQuestion(UUID userId, UpdateQuestionDto question);

    QuestionDto getQuestion(UUID id);
}
