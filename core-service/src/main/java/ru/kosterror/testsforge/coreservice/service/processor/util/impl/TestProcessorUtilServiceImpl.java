package ru.kosterror.testsforge.coreservice.service.processor.util.impl;

import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.service.processor.util.TestProcessorUtilService;

import java.util.List;
import java.util.UUID;

@Service
public class TestProcessorUtilServiceImpl<T extends Question> implements TestProcessorUtilService<T> {

    @Override
    public T findQuestion(List<T> questions, UUID questionId) {
        return questions.stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Question with id " + questionId + " not found"));
    }

    @Override
    public List<T> filterQuestions(List<Question> questions, QuestionType questionType, Class<T> classToCast) {
        return questions.stream()
                .filter(question -> question.getType().equals(questionType))
                .map(classToCast::cast)
                .toList();
    }
}