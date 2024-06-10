package ru.kosterror.forms.coreservice.mapper.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.forms.coreservice.dto.question.full.textinput.TextInputQuestionDto;
import ru.kosterror.forms.coreservice.dto.question.update.UpdateQuestionDto;
import ru.kosterror.forms.coreservice.dto.question.update.UpdateTextInputQuestionDto;
import ru.kosterror.forms.coreservice.entity.question.QuestionEntity;
import ru.kosterror.forms.coreservice.entity.question.textinput.TextInputQuestionEntity;

@Component
public class TextInputQuestionMapper extends BaseQuestionMapper {

    @Override
    public QuestionEntity toEntity(UpdateQuestionDto baseDto) {
        var entity = new TextInputQuestionEntity();
        mapBaseQuestionEntityFields(entity, baseDto);

        var dto = (UpdateTextInputQuestionDto) baseDto;

        entity.setPoints(dto.getPoints());
        entity.setIsCaseSensitive(dto.isCaseSensitive());
        entity.setAnswers(dto.getAnswers());

        return entity;
    }

    @Override
    public QuestionDto toDto(QuestionEntity baseEntity) {
        var dto = new TextInputQuestionDto();
        mapBaseQuestionDtoFields(dto, baseEntity);

        var entity = (TextInputQuestionEntity) baseEntity;

        dto.setPoints(entity.getPoints());
        dto.setCaseSensitive(entity.getIsCaseSensitive());
        dto.setAnswers(entity.getAnswers());

        return dto;
    }
}
