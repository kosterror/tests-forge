package ru.kosterror.testsforge.coreservice.service.processor.question.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.ChoiceOption;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.SingleChoiceQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.util.TestProcessorUtilService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SingleChoiceGeneratedTestProcessorImpl implements QuestionProcessor {

    private final TestProcessorUtilService<SingleChoiceQuestion, SingleChoiceQuestionEntity> testProcessorUtilService;

    @Override
    public void markQuestionAnswers(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing single choice question answers...");

        var singleChoiceQuestions = testProcessorUtilService.filterQuestions(
                questions,
                QuestionType.SINGLE_CHOICE,
                SingleChoiceQuestion.class
        );
        var answers = answersDto.singleChoiceAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var optionId = answerEntry.getValue();

            markAnswerToConcreteQuestion(
                    testProcessorUtilService.findQuestion(singleChoiceQuestions, questionId),
                    optionId
            );
        }

        log.info("Single choice question answers processed successfully");
    }

    @Override
    public int markQuestionAnswersAndCalculatePoints(List<Question> questions,
                                                     List<QuestionEntity> questionEntities,
                                                     AnswersDto answersDto) {
        log.info("Started processing single choice question answers and calculating points...");

        var points = 0;
        var answers = answersDto.singleChoiceAnswers();
        var singleChoiceQuestions = testProcessorUtilService.filterQuestions(
                questions,
                QuestionType.SINGLE_CHOICE,
                SingleChoiceQuestion.class
        );

        var singleChoiceQuestionEntities = testProcessorUtilService.filterQuestionEntities(
                questionEntities,
                QuestionType.SINGLE_CHOICE,
                SingleChoiceQuestionEntity.class
        );

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var optionId = answerEntry.getValue();

            var question = testProcessorUtilService.findQuestion(singleChoiceQuestions, questionId);
            var questionEntity = testProcessorUtilService.findQuestionEntity(
                    singleChoiceQuestionEntities,
                    question.getId()
            );

            markAnswerToConcreteQuestion(
                    question,
                    optionId
            );

            points += calculateAndSetPoints(question, questionEntity);
        }

        log.info("Single choice question answers processed and calculated points successfully");
        return points;
    }

    private int calculateAndSetPoints(SingleChoiceQuestion question,
                                      SingleChoiceQuestionEntity questionEntity
    ) {
        var optionId = question.getEnteredAnswerId();
        var optionEntity = testProcessorUtilService.getSingleOptionEntity(questionEntity, optionId);

        if (Boolean.TRUE.equals(optionEntity.getIsRight())) {
            question.setPoints(questionEntity.getPoints());
        } else {
            question.setPoints(0);
        }

        return question.getPoints();
    }


    private void markAnswerToConcreteQuestion(SingleChoiceQuestion question,
                                              UUID optionId
    ) {
        checkOptionExisting(question, optionId);

        question.setEnteredAnswerId(optionId);
    }

    private void checkOptionExisting(SingleChoiceQuestion question, UUID optionId) {
        var isOptionExists = question.getOptions()
                .stream()
                .map(ChoiceOption::getId)
                .anyMatch(oId -> oId.equals(optionId));

        if (!isOptionExists) {
            throw new NotFoundException(
                    "Option with id %s not found for question %s".formatted(optionId, question.getId())
            );
        }
    }
}
