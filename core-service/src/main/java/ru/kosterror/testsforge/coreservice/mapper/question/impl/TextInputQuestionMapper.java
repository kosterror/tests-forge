package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateTextInputQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.textinput.TextInputQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.textinput.TextInputQuestionEntity;
import ru.kosterror.testsforge.coreservice.mapper.SubjectMapper;

@Component
public class TextInputQuestionMapper extends BaseQuestionMapper {

    public TextInputQuestionMapper(SubjectMapper subjectMapper) {
        super(subjectMapper);
    }

    @Override
    public QuestionEntity toEntity(CreateQuestionDto baseDto) {
        var entity = new TextInputQuestionEntity();
        mapBaseQuestionEntityFields(entity, baseDto);

        var dto = (CreateTextInputQuestionDto) baseDto;

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
