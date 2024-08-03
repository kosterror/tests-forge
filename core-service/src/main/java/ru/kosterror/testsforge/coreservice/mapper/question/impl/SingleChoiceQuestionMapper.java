package ru.kosterror.testsforge.coreservice.mapper.question.impl;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateSingleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.single.SingleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.single.SingleOptionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleOptionEntity;
import ru.kosterror.testsforge.coreservice.mapper.SubjectMapper;

import java.util.ArrayList;

@Component
public class SingleChoiceQuestionMapper extends BaseQuestionMapper {

    public SingleChoiceQuestionMapper(SubjectMapper subjectMapper) {
        super(subjectMapper);
    }

    private static SingleOptionEntity mapToSingleOptionEntity(CreateSingleChoiceQuestionDto dto,
                                                              int optionOrder) {
        var optionEntity = new SingleOptionEntity();
        optionEntity.setName(dto.getOptions().get(optionOrder));
        optionEntity.setOrder(optionOrder);
        optionEntity.setIsRight(dto.getCorrectOptionIndex() == optionOrder);
        return optionEntity;
    }

    private static SingleOptionDto mapToSingleOptionDto(SingleOptionEntity optionEntity) {
        var optionDto = new SingleOptionDto();
        optionDto.setId(optionEntity.getId());
        optionDto.setName(optionEntity.getName());
        optionDto.setRight(optionEntity.getIsRight());

        return optionDto;
    }

    @Override
    public QuestionEntity toEntity(CreateQuestionDto baseDto) {
        var entity = new SingleChoiceQuestionEntity();
        mapBaseQuestionEntityFields(entity, baseDto);

        var dto = (CreateSingleChoiceQuestionDto) baseDto;

        entity.setPoints(dto.getPoints());

        var optionNames = dto.getOptions();
        var options = new ArrayList<SingleOptionEntity>(optionNames.size());

        for (int order = 0; order < optionNames.size(); order++) {
            var option = mapToSingleOptionEntity(dto, order);
            options.add(option);
        }

        entity.setOptions(options);

        return entity;
    }

    @Override
    public QuestionDto toDto(QuestionEntity baseEntity) {
        var dto = new SingleChoiceQuestionDto();
        dto.setOptions(new ArrayList<>());

        mapBaseQuestionDtoFields(dto, baseEntity);

        var entity = (SingleChoiceQuestionEntity) baseEntity;

        dto.setPoints(entity.getPoints());

        if (entity.getOptions() != null) {
            entity.getOptions()
                    .stream()
                    .map(SingleChoiceQuestionMapper::mapToSingleOptionDto)
                    .forEach(dto.getOptions()::add);
        }

        return dto;
    }
}
