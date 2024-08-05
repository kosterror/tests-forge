package ru.kosterror.testsforge.coreservice.service.impl.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Block;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.StaticBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BlockFactory {

    private final VariantFactory variantFactory;
    private final QuestionFactory questionFactory;
    private final Random random;

    public List<Block> buildBlocks(List<BlockEntity> blockEntities) {
        return blockEntities.stream()
                .map(this::buildBlock)
                .toList();
    }

    public Block buildBlock(BlockEntity blockEntity) {
        return switch (blockEntity.getType()) {
            case DYNAMIC -> buildDynamicBlock((DynamicBlockEntity) blockEntity);
            case STATIC -> buildStaticBlock((StaticBlockEntity) blockEntity);
        };
    }

    private Block buildStaticBlock(StaticBlockEntity blockEntity) {
        var variant = variantFactory.buildVariant(blockEntity.getVariants());

        return Block.builder()
                .id(blockEntity.getId())
                .type(blockEntity.getType())
                .name(blockEntity.getName())
                .description(blockEntity.getDescription())
                .variant(variant)
                .build();
    }

    private Block buildDynamicBlock(DynamicBlockEntity blockEntity) {
        var questionCount = blockEntity.getQuestionCount();
        var questionEntities = new ArrayList<>(blockEntity.getQuestions());
        var questionsForBlock = new ArrayList<Question>(questionCount);

        for (int i = 0; i < questionCount; i++) {
            var questionEntity = getRandomQuestionEntity(questionEntities);
            var question = questionFactory.buildQuestion(questionEntity);

            questionEntities.remove(questionEntity);
            questionsForBlock.add(question);
        }

        return Block.builder()
                .id(blockEntity.getId())
                .type(blockEntity.getType())
                .name(blockEntity.getName())
                .description(blockEntity.getDescription())
                .questions(questionsForBlock)
                .build();
    }

    private QuestionEntity getRandomQuestionEntity(List<QuestionEntity> questions) {
        var index = random.nextInt(questions.size());

        return questions.get(index);
    }
}
