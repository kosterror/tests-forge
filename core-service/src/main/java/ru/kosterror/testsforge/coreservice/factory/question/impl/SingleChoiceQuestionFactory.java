package ru.kosterror.testsforge.coreservice.factory.question.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.NewSingleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleOptionEntity;
import ru.kosterror.testsforge.coreservice.exception.InternalException;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class SingleChoiceQuestionFactory {

    private final CommonFieldQuestionMapper commonFieldQuestionMapper;

    public SingleChoiceQuestionEntity buildFromDto(NewSingleChoiceQuestionDto questionDto) {
        var questionEntity = new SingleChoiceQuestionEntity();
        commonFieldQuestionMapper.mapCommonFields(questionDto, questionEntity);

        questionEntity.setPoints(questionDto.getPoints());

        var optionNames = questionDto.getOptions();
        var options = new ArrayList<SingleOptionEntity>(optionNames.size());

        for (int order = 0; order < optionNames.size(); order++) {
            var option = buildOptionEntityFromDto(questionDto, order, questionEntity);
            options.add(option);
        }

        questionEntity.setOptions(options);

        return questionEntity;
    }

    public SingleChoiceQuestionEntity buildFromEntity(SingleChoiceQuestionEntity questionEntity) {
        var dto = new NewSingleChoiceQuestionDto();
        dto.setName(questionEntity.getName());
        dto.setAttachments(new ArrayList<>(questionEntity.getAttachments()));
        dto.setPoints(questionEntity.getPoints());
        dto.setOptions(questionEntity.getOptions().stream().map(SingleOptionEntity::getName).toList());
        dto.setCorrectOptionIndex(questionEntity.getOptions().stream()
                .filter(SingleOptionEntity::getIsRight)
                .map(SingleOptionEntity::getOrder)
                .findFirst()
                .orElseThrow(() ->
                        new InternalException("No correct option found for question " + questionEntity.getId())
                )
        );

        return buildFromDto(dto);
    }

    private SingleOptionEntity buildOptionEntityFromDto(NewSingleChoiceQuestionDto dto,
                                                        int optionOrder,
                                                        SingleChoiceQuestionEntity question) {
        var optionEntity = new SingleOptionEntity();
        optionEntity.setName(dto.getOptions().get(optionOrder));
        optionEntity.setOrder(optionOrder);
        optionEntity.setIsRight(dto.getCorrectOptionIndex() == optionOrder);
        optionEntity.setQuestion(question);
        return optionEntity;
    }
}
