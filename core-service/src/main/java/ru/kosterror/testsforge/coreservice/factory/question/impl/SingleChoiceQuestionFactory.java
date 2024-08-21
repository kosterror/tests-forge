package ru.kosterror.testsforge.coreservice.factory.question.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.NewSingleChoiceQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleOptionEntity;

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
        var newQuestionEntity = new SingleChoiceQuestionEntity();
        commonFieldQuestionMapper.mapCommonFields(questionEntity, newQuestionEntity);

        newQuestionEntity.setPoints(questionEntity.getPoints());

        var newOptions = new ArrayList<SingleOptionEntity>(questionEntity.getOptions().size());

        for (var option : questionEntity.getOptions()) {
            var newOption = new SingleOptionEntity();
            newOption.setName(option.getName());
            newOption.setOrder(option.getOrder());
            newOption.setIsRight(option.getIsRight());
            newOption.setQuestion(newQuestionEntity);

            newOptions.add(newOption);
        }

        newQuestionEntity.setOptions(newOptions);

        return newQuestionEntity;
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
