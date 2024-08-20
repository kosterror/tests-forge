package ru.kosterror.testsforge.coreservice.factory.test.pattern.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.NewDynamicBlockDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.factory.question.QuestionFactory;

@Component
@RequiredArgsConstructor
public class DynamicBlockFactory {

    private final QuestionFactory questionFactory;

    public DynamicBlockEntity buildFromDto(NewDynamicBlockDto blockDto) {
        var blockEntity = new DynamicBlockEntity();
        var questions = questionFactory.buildQuestionsForDynamicBlockFromDtos(blockDto.getQuestions(), blockEntity);

        blockEntity.setName(blockDto.getName());
        blockEntity.setDescription(blockDto.getDescription());
        blockEntity.setType(BlockType.DYNAMIC);
        blockEntity.setQuestionCount(blockDto.getQuestionCount());
        blockEntity.setQuestions(questions);

        return blockEntity;
    }

    public DynamicBlockEntity buildFromEntity(DynamicBlockEntity blockEntity) {
        var newBlockEntity = new DynamicBlockEntity();
        var newQuestions = questionFactory.buildQuestionsForDynamicBlockFromEntities(
                blockEntity.getQuestions(),
                newBlockEntity
        );

        newBlockEntity.setName(blockEntity.getName());
        newBlockEntity.setDescription(blockEntity.getDescription());
        newBlockEntity.setType(BlockType.DYNAMIC);
        newBlockEntity.setQuestionCount(blockEntity.getQuestionCount());
        newBlockEntity.setQuestions(newQuestions);

        return newBlockEntity;
    }

}
