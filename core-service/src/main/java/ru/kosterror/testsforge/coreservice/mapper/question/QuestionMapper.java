package ru.kosterror.testsforge.coreservice.mapper.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.mapper.question.impl.MatchingQuestionMapper;
import ru.kosterror.testsforge.coreservice.mapper.question.impl.MultipleChoiceQuestionMapper;
import ru.kosterror.testsforge.coreservice.mapper.question.impl.SingleChoiceQuestionMapper;
import ru.kosterror.testsforge.coreservice.mapper.question.impl.TextInputQuestionMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    private final MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;
    private final TextInputQuestionMapper textInputQuestionMapper;
    private final MatchingQuestionMapper matchingQuestionMapper;

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
