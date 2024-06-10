package ru.kosterror.forms.coreservice.mapper.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.forms.coreservice.dto.question.update.UpdateQuestionDto;
import ru.kosterror.forms.coreservice.entity.question.QuestionEntity;
import ru.kosterror.forms.coreservice.mapper.question.impl.MatchingQuestionMapper;
import ru.kosterror.forms.coreservice.mapper.question.impl.MultipleChoiceQuestionMapper;
import ru.kosterror.forms.coreservice.mapper.question.impl.SingleChoiceQuestionMapper;
import ru.kosterror.forms.coreservice.mapper.question.impl.TextInputQuestionMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    private final MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;
    private final TextInputQuestionMapper textInputQuestionMapper;
    private final MatchingQuestionMapper matchingQuestionMapper;

    public QuestionEntity toEntity(UpdateQuestionDto dto) {
        return switch (dto.getType()) {
            case SINGLE_CHOICE -> singleChoiceQuestionMapper.toEntity(dto);
            case MULTIPLE_CHOICE -> multipleChoiceQuestionMapper.toEntity(dto);
            case TEXT_INPUT -> textInputQuestionMapper.toEntity(dto);
            case MATCHING -> matchingQuestionMapper.toEntity(dto);
        };
    }

    public List<QuestionEntity> toEntities(List<UpdateQuestionDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }

    public QuestionDto toDto(QuestionEntity entity) {
        return switch (entity.getType()) {
            case SINGLE_CHOICE -> singleChoiceQuestionMapper.toDto(entity);
            case MULTIPLE_CHOICE -> multipleChoiceQuestionMapper.toDto(entity);
            case TEXT_INPUT -> textInputQuestionMapper.toDto(entity);
            case MATCHING -> matchingQuestionMapper.toDto(entity);
        };
    }

    public List<QuestionDto> toDtos(List<QuestionEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

}
