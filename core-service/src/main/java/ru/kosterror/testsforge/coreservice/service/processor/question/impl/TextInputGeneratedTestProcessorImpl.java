package ru.kosterror.testsforge.coreservice.service.processor.question.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.TextInputQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.util.TestProcessorUtilService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextInputGeneratedTestProcessorImpl implements QuestionProcessor {

    private final TestProcessorUtilService<TextInputQuestion> testProcessorUtilService;

    @Override
    public void markQuestionAnswers(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing text input question answers...");

        var textInputQuestions = testProcessorUtilService.filterQuestions(
                questions,
                QuestionType.TEXT_INPUT,
                TextInputQuestion.class
        );
        var answers = answersDto.textAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var enteredAnswer = answerEntry.getValue();

            var question = testProcessorUtilService.findQuestion(textInputQuestions, questionId);

            question.setEnteredAnswer(enteredAnswer);
        }

        log.info("Text input question answers processed successfully");
    }

}
