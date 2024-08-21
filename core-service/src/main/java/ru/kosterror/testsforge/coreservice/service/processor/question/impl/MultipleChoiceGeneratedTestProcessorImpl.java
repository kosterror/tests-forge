package ru.kosterror.testsforge.coreservice.service.processor.question.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.ChoiceOption;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.MultipleChoiceQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.util.TestProcessorUtilService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultipleChoiceGeneratedTestProcessorImpl implements QuestionProcessor {

    private final TestProcessorUtilService<MultipleChoiceQuestion, MultipleChoiceQuestionEntity>
            testProcessorUtilService;

    @Override
    public void markQuestionAnswers(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing multiple choice question answers...");

        var multipleChoiceQuestions = testProcessorUtilService.filterQuestions(questions,
                QuestionType.MULTIPLE_CHOICE,
                MultipleChoiceQuestion.class
        );

        var answers = answersDto.multipleChoiceAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var enteredOptionIds = answerEntry.getValue();

            markAnswerForConcreteQuestion(
                    testProcessorUtilService.findQuestion(multipleChoiceQuestions, questionId),
                    questionId,
                    enteredOptionIds
            );
        }

        log.info("Multiple choice question answers processed successfully");
    }

    @Override
    public int markQuestionAnswersAndCalculatePoints(List<Question> questions,
                                                     List<QuestionEntity> questionEntities,
                                                     AnswersDto answersDto
    ) {
        log.info("Started processing multiple choice question answers and calculating points...");

        var points = 0;

        var multipleChoiceQuestions = testProcessorUtilService.filterQuestions(questions,
                QuestionType.MULTIPLE_CHOICE,
                MultipleChoiceQuestion.class
        );

        var multipleChoiceQuestionEntities = testProcessorUtilService.filterQuestionEntities(
                questionEntities,
                QuestionType.MULTIPLE_CHOICE,
                MultipleChoiceQuestionEntity.class
        );

        var answers = answersDto.multipleChoiceAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var enteredOptionIds = answerEntry.getValue();

            var question = testProcessorUtilService.findQuestion(multipleChoiceQuestions, questionId);
            var questionEntity = testProcessorUtilService.findQuestionEntity(multipleChoiceQuestionEntities, questionId);

            markAnswerForConcreteQuestion(
                    testProcessorUtilService.findQuestion(multipleChoiceQuestions, questionId),
                    questionId,
                    enteredOptionIds
            );

            points += calculateAndSetPoints(question, questionEntity);
        }

        log.info("Finished processing multiple choice question answers and calculating points");
        return points;
    }

    private int calculateAndSetPoints(MultipleChoiceQuestion question, MultipleChoiceQuestionEntity questionEntity) {
        var enteredOptionIds = question.getEnteredAnswers();

        int rightAnswersCount = 0;

        for (var optionId : enteredOptionIds) {
            var option = testProcessorUtilService.getMultipleOptionEntity(questionEntity, optionId);

            if (Boolean.TRUE.equals(option.getIsRight())) {
                rightAnswersCount++;
            }
        }

        var incorrectAnswersCount = enteredOptionIds.size() - rightAnswersCount;
        rightAnswersCount = rightAnswersCount - incorrectAnswersCount;

        var points = calculatePoints(rightAnswersCount, questionEntity.getPoints());
        question.setPoints(points);

        return points;
    }

    private int calculatePoints(int rightAnswersCount, Map<Integer, Integer> pointsMap) {
        for (int i = rightAnswersCount; i >= 0; i--) {
            if (pointsMap.containsKey(i)) {
                return pointsMap.get(i);
            }
        }

        return 0;
    }

    private void markAnswerForConcreteQuestion(MultipleChoiceQuestion question,
                                               UUID questionId,
                                               List<UUID> enteredOptionIds
    ) {
        checkOptionExisting(question, questionId, enteredOptionIds);

        question.setEnteredAnswers(enteredOptionIds);
    }

    private void checkOptionExisting(MultipleChoiceQuestion question,
                                     UUID questionId,
                                     List<UUID> enteredOptionIds
    ) {
        var questionOptionIds = question.getOptions()
                .stream()
                .map(ChoiceOption::getId)
                .toList();


        var notFoundOptionIds = enteredOptionIds.stream()
                .filter(optionId -> !questionOptionIds.contains(optionId))
                .toList();

        if (!notFoundOptionIds.isEmpty()) {
            throw new NotFoundException(
                    "Options with ids %s not found for question %s".formatted(notFoundOptionIds, questionId)
            );
        }
    }


}
