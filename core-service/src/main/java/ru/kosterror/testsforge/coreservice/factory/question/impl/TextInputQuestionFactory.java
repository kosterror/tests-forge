package ru.kosterror.testsforge.coreservice.factory.question.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.NewTextInputQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.textinput.TextInputQuestionEntity;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class TextInputQuestionFactory {

    private final CommonFieldQuestionMapper commonFieldQuestionMapper;

    public TextInputQuestionEntity buildFromDto(NewTextInputQuestionDto questionDto) {
        var questionEntity = new TextInputQuestionEntity();
        commonFieldQuestionMapper.mapCommonFields(questionDto, questionEntity);

        questionEntity.setPoints(questionDto.getPoints());
        questionEntity.setIsCaseSensitive(questionDto.isCaseSensitive());
        questionEntity.setAnswers(questionDto.getAnswers());

        return questionEntity;
    }

    public TextInputQuestionEntity buildFromEntity(TextInputQuestionEntity questionEntity) {
        var newQuestionEntity = new TextInputQuestionEntity();
        commonFieldQuestionMapper.mapCommonFields(questionEntity, newQuestionEntity);

        newQuestionEntity.setPoints(questionEntity.getPoints());
        newQuestionEntity.setIsCaseSensitive(questionEntity.getIsCaseSensitive());
        newQuestionEntity.setAnswers(new ArrayList<>(questionEntity.getAnswers()));

        return newQuestionEntity;
    }
}
