package ru.kosterror.testsforge.coreservice.service.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.ChoiceOption;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.SingleChoiceQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SingleChoiceQuestionProcessor extends DefaultQuestionProcessor<SingleChoiceQuestion> {

    @Override
    public void process(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing single choice question answers...");

        var singleChoiceQuestions = filterQuestions(questions, QuestionType.SINGLE_CHOICE, SingleChoiceQuestion.class);
        var answers = answersDto.singleChoiceAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var optionId = answerEntry.getValue();

            processConcreteAnswer(
                    findQuestion(singleChoiceQuestions, questionId),
                    questionId,
                    optionId
            );
        }

        log.info("Single choice question answers processed successfully");
    }

    private void processConcreteAnswer(SingleChoiceQuestion question,
                                       UUID questionId,
                                       UUID optionId
    ) {
        checkOptionExisting(question, questionId, optionId);

        question.setEnteredAnswerId(optionId);
    }

    private void checkOptionExisting(SingleChoiceQuestion question, UUID questionId, UUID optionId) {
        var isOptionExists = question.getOptions()
                .stream()
                .map(ChoiceOption::getId)
                .anyMatch(oId -> oId.equals(optionId));

        if (!isOptionExists) {
            throw new NotFoundException(
                    "Option with id %s not found for question %s".formatted(optionId, questionId)
            );
        }
    }
}
