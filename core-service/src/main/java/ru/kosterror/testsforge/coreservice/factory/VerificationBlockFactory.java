package ru.kosterror.testsforge.coreservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationBlock;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Block;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class VerificationBlockFactory {

    private final VerificationQuestionFactory verificationQuestionFactory;
    private final VerificationVariantFactory verificationVariantFactory;

    public List<VerificationBlock> build(List<Block> blocks, Map<UUID, QuestionDto> questionsWithCorrectAnswer) {
        return Stream.ofNullable(blocks)
                .flatMap(List::stream)
                .map(block -> build(block, questionsWithCorrectAnswer))
                .toList();
    }

    private VerificationBlock build(Block block, Map<UUID, QuestionDto> questionsWithCorrectAnswer) {
        var variant = verificationVariantFactory.build(block.getVariant(), questionsWithCorrectAnswer);
        var questions = verificationQuestionFactory.build(block.getQuestions(), questionsWithCorrectAnswer);

        return new VerificationBlock(
                block.getId(),
                block.getType(),
                block.getName(),
                block.getDescription(),
                variant,
                questions
        );
    }
}
