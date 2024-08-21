package ru.kosterror.testsforge.coreservice.service.processor.test.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.CheckTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.exception.InternalException;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.test.GeneratedTestProcessor;
import ru.kosterror.testsforge.coreservice.service.util.TestUtilService;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratedTestProcessorImpl implements GeneratedTestProcessor {

    private final List<QuestionProcessor> questionProcessors;
    private final TestUtilService testUtilService;

    @Override
    public void markAnswers(GeneratedTestEntity generatedTest, AnswersDto answers) {
        log.info("Started processing generated test {} answers...", generatedTest.getId());

        var questions = testUtilService.extractQuestions(generatedTest);

        questionProcessors.forEach(questionProcessor -> questionProcessor.markQuestionAnswers(questions, answers));

        log.info("Generated test {} answers processed successfully", generatedTest.getId());
    }

    @Override
    public void markAnswersAndCalculatePoints(GeneratedTestEntity generatedTest,
                                              TestPatternEntity testPattern,
                                              AnswersDto answers
    ) {
        log.info("Started processing generated test {} answers and calculating points...", generatedTest.getId());

        var questions = testUtilService.extractQuestions(generatedTest);
        var questionEntities = testUtilService.extractQuestionEntities(testPattern);

        int points = 0;

        for (var questionProcessor : questionProcessors) {
            points += questionProcessor.markQuestionAnswersAndCalculatePoints(
                    questions,
                    questionEntities,
                    answers
            );
        }

        generatedTest.setPoints(points);

        log.info("Generated test {} answers processed and points calculated successfully", generatedTest.getId());
    }

    @Override
    public void verifyTest(GeneratedTestEntity generatedTest, CheckTestDto checkTestDto) {
        log.info("Started verifying generated test {}...", generatedTest.getId());

        var questions = testUtilService.extractQuestions(generatedTest);

        if (checkTestDto.points() != null) {
            for (var points : checkTestDto.points()) {
                var question = testUtilService.findQuestionById(questions, points.getFirst());

                applyNewPoints(generatedTest, question, points.getSecond());
            }
        }

        generatedTest.setStatus(checkTestDto.status());

        log.info("Generated test {} verified successfully", generatedTest.getId());
    }

    @Override
    public void calculateMark(GeneratedTestEntity generatedTest, Map<Integer, String> marks) {
        log.info("Started recalculating generated test {} mark...", generatedTest.getId());

        var pointsForTest = generatedTest.getPoints();

        var pointsForMarks = marks.keySet()
                .stream()
                .sorted()
                .toList();

        for (int i = pointsForMarks.size() - 1; i >= 0; i--) {
            if (pointsForTest >= pointsForMarks.get(i)) {
                generatedTest.setMark(marks.get(pointsForMarks.get(i)));
                log.info("Generated test {} mark recalculated successfully", generatedTest.getId());
                return;
            }
        }

        log.info("Generated test {} mark was not recalculated", generatedTest.getId());
    }

    private void applyNewPoints(GeneratedTestEntity generatedTest,
                                Question question,
                                Integer newQuestionPoints
    ) {
        var oldPoints = generatedTest.getPoints();
        var oldQuestionPoints = question.getPoints();

        if (oldPoints == null) {
            throw new InternalException("Points of generated test %s are null".formatted(generatedTest.getId()));
        }

        if (newQuestionPoints == null) {
            question.setPoints(null);

            if (oldQuestionPoints != null) {
                generatedTest.setPoints(oldPoints - oldQuestionPoints);
            }

            return;
        }

        if (oldQuestionPoints == null) {
            question.setPoints(newQuestionPoints);
            generatedTest.setPoints(oldPoints + newQuestionPoints);

            return;
        }

        var difference = oldQuestionPoints - newQuestionPoints;
        question.setPoints(newQuestionPoints);
        generatedTest.setPoints(oldPoints - difference);
    }


}

