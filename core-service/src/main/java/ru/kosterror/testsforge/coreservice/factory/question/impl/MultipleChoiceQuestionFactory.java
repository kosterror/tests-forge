package ru.kosterror.testsforge.coreservice.factory.question.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.NewMultipleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleOptionEntity;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class MultipleChoiceQuestionFactory {

    private final CommonFieldQuestionMapper commonFieldQuestionMapper;

    public MultipleChoiceQuestionEntity buildFromDto(NewMultipleChoiceQuestionDto questionDto) {
        var questionEntity = new MultipleChoiceQuestionEntity();
        commonFieldQuestionMapper.mapCommonFields(questionDto, questionEntity);

        var optionNames = questionDto.getOptions();
        var options = new ArrayList<MultipleOptionEntity>(optionNames.size());

        for (int order = 0; order < optionNames.size(); order++) {
            var option = buildMultipleOptionEntityFromDto(questionDto, order, questionEntity);
            options.add(option);
        }

        questionEntity.setOptions(options);
        questionEntity.setPoints(questionDto.getPoints());

        return questionEntity;
    }

    public MultipleChoiceQuestionEntity buildFromEntity(MultipleChoiceQuestionEntity questionEntity) {
        var dto = new NewMultipleChoiceQuestionDto();
        dto.setName(questionEntity.getName());
        dto.setAttachments(new ArrayList<>(questionEntity.getAttachments()));
        dto.setPoints(new HashMap<>(questionEntity.getPoints()));
        dto.setOptions(questionEntity.getOptions().stream().map(MultipleOptionEntity::getName).toList());
        dto.setCorrectOptionIndices(questionEntity.getOptions().stream()
                .filter(MultipleOptionEntity::getIsRight)
                .map(MultipleOptionEntity::getOrder)
                .toList());

        return buildFromDto(dto);
    }

    private MultipleOptionEntity buildMultipleOptionEntityFromDto(NewMultipleChoiceQuestionDto dto,
                                                                  int order,
                                                                  MultipleChoiceQuestionEntity question) {
        var optionEntity = new MultipleOptionEntity();
        optionEntity.setName(dto.getOptions().get(order));
        optionEntity.setOrder(order);
        optionEntity.setIsRight(dto.getCorrectOptionIndices().contains(order));
        optionEntity.setQuestion(question);
        return optionEntity;
    }
}
