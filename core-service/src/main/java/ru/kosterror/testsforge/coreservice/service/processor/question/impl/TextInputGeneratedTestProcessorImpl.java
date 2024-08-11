package ru.kosterror.testsforge.coreservice.service.processor.question.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.TextInputQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.textinput.TextInputQuestionEntity;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.util.TestProcessorUtilService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextInputGeneratedTestProcessorImpl implements QuestionProcessor {

    private final TestProcessorUtilService<TextInputQuestion, TextInputQuestionEntity> testProcessorUtilService;

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

    @Override
    public int markQuestionAnswersAndCalculatePoints(List<Question> questions,
                                                     List<QuestionEntity> questionEntities,
                                                     AnswersDto answersDto
    ) {
        log.info("Started processing text input question answers and calculating points...");

        var points = 0;

        var textInputQuestions = testProcessorUtilService.filterQuestions(
                questions,
                QuestionType.TEXT_INPUT,
                TextInputQuestion.class
        );

        var textInputQuestionEntities = testProcessorUtilService.filterQuestionEntities(
                questionEntities,
                QuestionType.TEXT_INPUT,
                TextInputQuestionEntity.class
        );

        var answers = answersDto.textAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var enteredAnswer = answerEntry.getValue();

            points += markConcreteQuestionAnswerAndCalculatePoints(
                    enteredAnswer,
                    testProcessorUtilService.findQuestion(textInputQuestions, questionId),
                    testProcessorUtilService.findQuestionEntity(textInputQuestionEntities, questionId)
            );
        }

        log.info("Processing text input question answers and calculating points finished successfully");
        return points;
    }

    private int markConcreteQuestionAnswerAndCalculatePoints(String enteredAnswer,
                                                             TextInputQuestion question,
                                                             TextInputQuestionEntity questionEntity
    ) {
        question.setEnteredAnswer(enteredAnswer);
        return calculateAndSetPoints(questionEntity, enteredAnswer, question);
    }

    private int calculateAndSetPoints(TextInputQuestionEntity questionEntity,
                                      String enteredAnswer,
                                      TextInputQuestion question
    ) {
        var isEnteredAnswerRight = questionEntity.getAnswers()
                .stream()
                .anyMatch(answer -> {
                    if (Boolean.TRUE.equals(questionEntity.getIsCaseSensitive())) {
                        return answer.equals(enteredAnswer);
                    } else {
                        return answer.equalsIgnoreCase(enteredAnswer);
                    }
                });

        if (isEnteredAnswerRight) {
            question.setPoints(questionEntity.getPoints());
        } else {
            question.setPoints(0);
        }

        return question.getPoints();
    }

}
