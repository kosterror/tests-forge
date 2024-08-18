package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.multiple.MultipleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.multiple.MultipleOptionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleOptionEntity;
import ru.kosterror.testsforge.coreservice.mapper.SubjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class MultipleChoiceQuestionMapper extends BaseQuestionMapper {

    public MultipleChoiceQuestionMapper(SubjectMapper subjectMapper) {
        super(subjectMapper);
    }

    @Override
    public QuestionDto toDto(QuestionEntity baseEntity) {
        var dto = new MultipleChoiceQuestionDto();
        mapBaseQuestionDtoFields(dto, baseEntity);

        var entity = (MultipleChoiceQuestionEntity) baseEntity;

        dto.setPoints(entity.getPoints());

        if (entity.getOptions() != null) {
            dto.setOptions(mapToMultipleOptionDtos(entity.getOptions()));
        }

        return dto;
    }

    private List<MultipleOptionDto> mapToMultipleOptionDtos(List<MultipleOptionEntity> options) {
        var optionDtos = new ArrayList<MultipleOptionDto>(options.size());

        for (var option : options) {
            var optionDto = new MultipleOptionDto();
            optionDto.setId(option.getId());
            optionDto.setName(option.getName());
            optionDto.setRight(option.getIsRight());
            optionDtos.add(optionDto);
        }

        return optionDtos;
    }
}
