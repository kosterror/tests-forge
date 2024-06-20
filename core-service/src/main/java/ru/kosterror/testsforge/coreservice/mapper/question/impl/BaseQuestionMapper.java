package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.update.UpdateQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionEntity;

public abstract class BaseQuestionMapper {

    public abstract QuestionEntity toEntity(UpdateQuestionDto dto);

    public abstract QuestionDto toDto(QuestionEntity entity);

    protected void mapBaseQuestionEntityFields(QuestionEntity entity, UpdateQuestionDto dto) {
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setAttachments(dto.getAttachments());
    }

    protected void mapBaseQuestionDtoFields(QuestionDto dto, QuestionEntity entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwnerId(entity.getOwnerId());
        dto.setType(entity.getType());
        dto.setAttachments(entity.getAttachments());
    }

}
