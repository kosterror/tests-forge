package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.multiple.MultipleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.multiple.MultipleOptionDto;
import ru.kosterror.testsforge.coreservice.dto.question.update.CreateMultipleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.update.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.question.multiple.MultipleOptionEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class MultipleChoiceQuestionMapper extends BaseQuestionMapper {

    private static MultipleOptionEntity mapToMultipleOptionEntity(CreateMultipleChoiceQuestionDto dto,
                                                                  int order) {
        var optionEntity = new MultipleOptionEntity();
        optionEntity.setName(dto.getOptions().get(order));
        optionEntity.setOrder(order);
        optionEntity.setIsRight(dto.getCorrectOptionIndices().contains(order));
        return optionEntity;
    }

    @Override
    public QuestionEntity toEntity(CreateQuestionDto baseDto) {
        var entity = new MultipleChoiceQuestionEntity();
        mapBaseQuestionEntityFields(entity, baseDto);

        var dto = (CreateMultipleChoiceQuestionDto) baseDto;

        var optionNames = dto.getOptions();
        var options = new ArrayList<MultipleOptionEntity>(optionNames.size());

        for (int order = 0; order < optionNames.size(); order++) {
            var option = mapToMultipleOptionEntity(dto, order);
            options.add(option);
        }

        entity.setOptions(options);

        return entity;
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
