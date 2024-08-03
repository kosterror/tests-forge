package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    QuestionDto createQuestion(UUID userId, UUID subjectId, CreateQuestionDto question);

    QuestionDto getQuestion(UUID id);

    void deleteQuestion(UUID id);

    PaginationResponse<QuestionDto> getQuestions(UUID subjectId,
                                                 String name,
                                                 List<QuestionType> types,
                                                 int page,
                                                 int size
    );
}
