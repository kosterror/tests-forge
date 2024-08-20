package ru.kosterror.testsforge.coreservice.factory.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.*;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.VariantEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.factory.question.impl.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionFactory {

    private final MatchingQuestionFactory matchingQuestionFactory;
    private final MultipleChoiceQuestionFactory multipleChoiceQuestionFactory;
    private final SingleChoiceQuestionFactory singleChoiceQuestionFactory;
    private final TextInputQuestionFactory textInputQuestionFactory;
    private final BasedOnExistingQuestionFactory basedOnExistingQuestionFactory;

    public QuestionEntity buildQuestionFromDto(CreateQuestionDto questionDto) {
        return switch (questionDto.getType()) {
            case MATCHING -> matchingQuestionFactory.buildFromDto((NewMatchingQuestionDto) questionDto);
            case MULTIPLE_CHOICE ->
                    multipleChoiceQuestionFactory.buildFromDto((NewMultipleChoiceQuestionDto) questionDto);
            case SINGLE_CHOICE -> singleChoiceQuestionFactory.buildFromDto((NewSingleChoiceQuestionDto) questionDto);
            case TEXT_INPUT -> textInputQuestionFactory.buildFromDto((NewTextInputQuestionDto) questionDto);
            case BASED_ON_EXISTING ->
                    basedOnExistingQuestionFactory.buildFromDto((CreateQuestionBasedOnExistingDto) questionDto);
        };
    }

    public List<QuestionEntity> buildQuestionsForDynamicBlockFromDtos(List<CreateQuestionDto> questionDtos,
                                                                      DynamicBlockEntity blockEntity
    ) {
        var questionEntities = new ArrayList<QuestionEntity>(questionDtos.size());

        for (CreateQuestionDto questionDto : questionDtos) {
            var questionEntity = buildQuestionFromDto(questionDto);
            questionEntity.setDynamicBlock(blockEntity);

            questionEntities.add(questionEntity);
        }

        return questionEntities;
    }

    public List<QuestionEntity> buildQuestionsForDynamicBlockFromEntities(
            List<QuestionEntity> questionEntities,
            DynamicBlockEntity blockEntity
    ) {
        var newQuestionEntities = new ArrayList<QuestionEntity>(questionEntities.size());

        for (QuestionEntity questionEntity : questionEntities) {
            var newQuestionEntity = buildQuestionFromEntity(questionEntity);
            newQuestionEntity.setDynamicBlock(blockEntity);

            newQuestionEntities.add(newQuestionEntity);
        }

        return newQuestionEntities;
    }

    public List<QuestionEntity> buildQuestionsForVariantFromDtos(List<CreateQuestionDto> questionDtos,
                                                                 VariantEntity variantEntity
    ) {
        var questionEntities = new ArrayList<QuestionEntity>(questionDtos.size());
        for (int i = 0; i < questionDtos.size(); i++) {
            var questionDto = questionDtos.get(i);

            var questionEntity = buildQuestionFromDto(questionDto);
            questionEntity.setVariant(variantEntity);
            questionEntity.setOrder(i);

            questionEntities.add(questionEntity);
        }

        return questionEntities;
    }

    public List<QuestionEntity> buildQuestionsForVariantFromEntities(
            List<QuestionEntity> questionEntities,
            VariantEntity variantEntity
    ) {
        var newQuestionEntities = new ArrayList<QuestionEntity>(questionEntities.size());

        for (int i = 0; i < questionEntities.size(); i++) {
            var questionEntity = questionEntities.get(i);
            var newQuestionEntity = buildQuestionFromEntity(questionEntity);
            newQuestionEntity.setVariant(variantEntity);
            newQuestionEntity.setOrder(i);

            newQuestionEntities.add(newQuestionEntity);
        }

        return newQuestionEntities;
    }

    private QuestionEntity buildQuestionFromEntity(QuestionEntity questionEntity) {
        var questionDto = new CreateQuestionBasedOnExistingDto();
        questionDto.setQuestionId(questionEntity.getId());

        return buildQuestionFromDto(questionDto);
    }

}
