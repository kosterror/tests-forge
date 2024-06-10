package ru.kosterror.forms.coreservice.mapper.question.impl;

import ru.kosterror.forms.coreservice.dto.question.createupdate.CreateUpdateQuestionDto;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.forms.coreservice.entity.question.QuestionEntity;

public abstract class BaseQuestionMapper {

    public abstract QuestionEntity toEntity(CreateUpdateQuestionDto dto);

    public abstract QuestionDto toDto(QuestionEntity entity);

    protected void mapBaseQuestionEntityFields(QuestionEntity entity, CreateUpdateQuestionDto dto) {
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
