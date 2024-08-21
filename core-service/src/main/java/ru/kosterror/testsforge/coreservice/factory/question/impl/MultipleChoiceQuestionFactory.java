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
            var option = buildOptionFromDto(questionDto, order, questionEntity);
            options.add(option);
        }

        questionEntity.setOptions(options);
        questionEntity.setPoints(questionDto.getPoints());

        return questionEntity;
    }

    private MultipleOptionEntity buildOptionFromDto(NewMultipleChoiceQuestionDto dto,
                                                    int order,
                                                    MultipleChoiceQuestionEntity question) {
        var optionEntity = new MultipleOptionEntity();
        optionEntity.setName(dto.getOptions().get(order));
        optionEntity.setOrder(order);
        optionEntity.setIsRight(dto.getCorrectOptionIndices().contains(order));
        optionEntity.setQuestion(question);
        return optionEntity;
    }

    public MultipleChoiceQuestionEntity buildFromEntity(MultipleChoiceQuestionEntity questionEntity) {
        var newQuestionEntity = new MultipleChoiceQuestionEntity();
        var newOptions = new ArrayList<MultipleOptionEntity>(questionEntity.getOptions().size());
        newQuestionEntity.setPoints(new HashMap<>(questionEntity.getPoints()));

        commonFieldQuestionMapper.mapCommonFields(questionEntity, newQuestionEntity);

        for (var option : questionEntity.getOptions()) {
            var newOption = buildOptionFromEntity(option, newQuestionEntity);
            newOptions.add(newOption);
        }

        newQuestionEntity.setOptions(newOptions);

        return newQuestionEntity;
    }

    private MultipleOptionEntity buildOptionFromEntity(MultipleOptionEntity option,
                                                       MultipleChoiceQuestionEntity newQuestionEntity
    ) {
        var newOption = new MultipleOptionEntity();
        newOption.setName(option.getName());
        newOption.setOrder(option.getOrder());
        newOption.setIsRight(option.getIsRight());
        newOption.setQuestion(newQuestionEntity);

        return newOption;
    }
}
