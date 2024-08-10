package ru.kosterror.testsforge.coreservice.service.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.TextInputQuestion;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;

@Slf4j
@Service
public class TextInputQuestionProcessor extends DefaultQuestionProcessor<TextInputQuestion> {

    @Override
    public void process(List<Question> questions, AnswersDto answersDto) {
        log.info("Started processing text input question answers...");

        var textInputQuestions = filterQuestions(questions, QuestionType.TEXT_INPUT, TextInputQuestion.class);
        var answers = answersDto.textAnswers();

        for (var answerEntry : answers.entrySet()) {
            var questionId = answerEntry.getKey();
            var enteredAnswer = answerEntry.getValue();

            var question = findQuestion(textInputQuestions, questionId);

            question.setEnteredAnswer(enteredAnswer);
        }

        log.info("Text input question answers processed successfully");
    }

}
