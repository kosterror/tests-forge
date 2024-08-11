package ru.kosterror.testsforge.coreservice.service.processor.question.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.MatchingOption;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.MatchingQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.util.TestProcessorUtilService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingGeneratedTestProcessorImpl implements QuestionProcessor {

    private final TestProcessorUtilService<MatchingQuestion, MatchingQuestionEntity> testProcessorUtilService;

    public void markQuestionAnswers(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing matching question answers...");

        var matchingQuestions = testProcessorUtilService.filterQuestions(
                questions,
                QuestionType.MATCHING,
                MatchingQuestion.class
        );
        var answers = answersDto.matchingAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var termDefinitionPairList = answerEntry.getValue();


            markAnswerForConcreteQuestion(
                    questionId,
                    termDefinitionPairList,
                    testProcessorUtilService.findQuestion(matchingQuestions, questionId)
            );
        }

        log.info("Matching question answers processed successfully");
    }

    @Override
    public int markQuestionAnswersAndCalculatePoints(List<Question> questions,
                                                     List<QuestionEntity> questionEntities,
                                                     AnswersDto answersDto
    ) {
        log.info("Started processing matching question answers and calculating points...");

        var points = 0;

        var matchingQuestions = testProcessorUtilService.filterQuestions(
                questions,
                QuestionType.MATCHING,
                MatchingQuestion.class
        );
        var matchingQuestionEntities = testProcessorUtilService.filterQuestionEntities(
                questionEntities,
                QuestionType.MATCHING,
                MatchingQuestionEntity.class
        );
        var answers = answersDto.matchingAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var termDefinitionPairList = answerEntry.getValue();

            var question = testProcessorUtilService.findQuestion(matchingQuestions, questionId);

            markAnswerForConcreteQuestion(questionId, termDefinitionPairList, question);

            var questionEntity = testProcessorUtilService.findQuestionEntity(matchingQuestionEntities, questionId);

            points += calculateAndSetPoints(question, questionEntity);
        }

        log.info("Matching question answers processed and calculated points successfully");

        return points;
    }

    private int calculateAndSetPoints(MatchingQuestion question, MatchingQuestionEntity questionEntity) {
        var termDefinitionPairs = testProcessorUtilService.extractTermDefinitionPairs(questionEntity);
        var enteredTermDefinitionPairs = question.getEnteredAnswersIndexes();

        var countRightAnswers = 0;

        for (var enteredTermDefinitionPair : enteredTermDefinitionPairs) {
            if (termDefinitionPairs.contains(enteredTermDefinitionPair)) {
                countRightAnswers++;
            }
        }

        return calculatePoints(questionEntity.getPoints(), countRightAnswers);
    }

    private int calculatePoints(Map<Integer, Integer> points, int countRightAnswers) {
        for (int i = countRightAnswers; i >= 0; i--) {
            if (points.containsKey(i)) {
                return points.get(i);
            }
        }

        return 0;
    }

    private void markAnswerForConcreteQuestion(UUID questionId,
                                               List<Pair<UUID, UUID>> termDefinitionPairList,
                                               MatchingQuestion question
    ) {
        var enteredAnswerPairList = new ArrayList<Pair<UUID, UUID>>();

        var termIds = question.getTerms()
                .stream()
                .map(MatchingOption::getId)
                .toList();

        var definitionIds = question.getDefinitions()
                .stream()
                .map(MatchingOption::getId)
                .toList();

        for (var termDefinitionPair : termDefinitionPairList) {
            var termId = termDefinitionPair.getFirst();
            var definitionId = termDefinitionPair.getSecond();

            checkTermExisting(termIds,
                    termId,
                    questionId, "Term with id %s not found for question %s"
            );

            checkTermExisting(definitionIds,
                    definitionId,
                    questionId, "Definition with id %s not found for question %s"
            );

            enteredAnswerPairList.add(Pair.of(termId, definitionId));
        }

        question.setEnteredAnswersIndexes(enteredAnswerPairList);
    }

    private void checkTermExisting(List<UUID> ids, UUID id, UUID questionId, String exceptionMessage) {
        if (!ids.contains(id)) {
            throw new NotFoundException(
                    exceptionMessage.formatted(id, questionId)
            );
        }
    }

}
