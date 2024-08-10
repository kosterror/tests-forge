package ru.kosterror.testsforge.coreservice.service.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.ChoiceOption;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.MultipleChoiceQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MultipleChoiceQuestionProcessor extends DefaultQuestionProcessor<MultipleChoiceQuestion> {

    @Override
    public void process(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing multiple choice question answers...");

        var multipleChoiceQuestions = filterQuestions(questions,
                QuestionType.MULTIPLE_CHOICE,
                MultipleChoiceQuestion.class
        );

        var answers = answersDto.multipleChoiceAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var enteredOptionIds = answerEntry.getValue();

            processConcreteQuestionAnswer(
                    findQuestion(multipleChoiceQuestions, questionId),
                    questionId,
                    enteredOptionIds
            );
        }

        log.info("Multiple choice question answers processed successfully");
    }

    private void processConcreteQuestionAnswer(MultipleChoiceQuestion question,
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
