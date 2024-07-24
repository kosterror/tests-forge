package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import lombok.RequiredArgsConstructor;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.mapper.SubjectMapper;

@RequiredArgsConstructor
public abstract class BaseQuestionMapper {

    private final SubjectMapper subjectMapper;

    public abstract QuestionEntity toEntity(CreateQuestionDto dto);

    public abstract QuestionDto toDto(QuestionEntity entity);

    protected void mapBaseQuestionEntityFields(QuestionEntity entity, CreateQuestionDto dto) {
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
        dto.setSubject(subjectMapper.toDto(entity.getSubject()));
    }

}
