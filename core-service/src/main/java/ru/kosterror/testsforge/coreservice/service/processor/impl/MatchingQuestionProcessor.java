package ru.kosterror.testsforge.coreservice.service.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.MatchingOption;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.MatchingQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MatchingQuestionProcessor extends DefaultQuestionProcessor<MatchingQuestion> {

    @Override
    public void process(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing matching question answers...");

        var matchingQuestions = filterQuestions(questions, QuestionType.MATCHING, MatchingQuestion.class);
        var answers = answersDto.matchingAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var termDefinitionPairList = answerEntry.getValue();


            processConcreteQuestionAnswer(
                    questionId,
                    termDefinitionPairList,
                    findQuestion(matchingQuestions, questionId)
            );
        }

        log.info("Matching question answers processed successfully");
    }

    private void processConcreteQuestionAnswer(UUID questionId,
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
