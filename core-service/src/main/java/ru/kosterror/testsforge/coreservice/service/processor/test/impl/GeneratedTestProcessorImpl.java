package ru.kosterror.testsforge.coreservice.service.processor.test.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.test.GeneratedTestProcessor;
import ru.kosterror.testsforge.coreservice.service.util.TestUtilService;

import java.util.List;

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


}

