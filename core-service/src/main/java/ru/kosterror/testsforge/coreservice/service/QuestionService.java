package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.update.CreateQuestionDto;

import java.util.UUID;

public interface QuestionService {

    QuestionDto createQuestion(UUID userId, CreateQuestionDto question);

    QuestionDto getQuestion(UUID id);
}
