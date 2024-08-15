package ru.kosterror.testsforge.coreservice.service.util.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Variant;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.DynamicBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.StaticBlockEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;
import ru.kosterror.testsforge.coreservice.service.util.TestUtilService;

import java.util.*;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TestUtilServiceImpl implements TestUtilService {

    private final QuestionMapper questionMapper;

    @Override
    public List<QuestionEntity> extractQuestionEntities(TestPatternEntity testPattern) {
        return testPattern.getPartitions()
                .stream()
                .flatMap(partition -> partition.getBlocks().stream())
                .flatMap(block ->
                        switch (block.getType()) {
                            case DYNAMIC -> Optional.of((DynamicBlockEntity) block)
                                    .stream()
                                    .flatMap(dynamicBlock -> dynamicBlock.getQuestions().stream());
                            case STATIC -> Optional.of((StaticBlockEntity) block)
                                    .stream()
                                    .flatMap(b -> b.getVariants().stream())
                                    .flatMap(variant -> variant.getQuestions().stream());
                        }
                ).toList();
    }

    @Override
    public List<Question> extractQuestions(GeneratedTestEntity generatedTest) {
        return generatedTest.getPartitions()
                .stream()
                .flatMap(partition -> partition.getBlocks().stream())
                .flatMap(block -> {
                            var blockQuestions = Optional.ofNullable(block.getQuestions())
                                    .stream()
                                    .flatMap(Collection::stream);

                            var variantQuestions = Optional.ofNullable(block.getVariant())
                                    .map(Variant::getQuestions)
                                    .stream()
                                    .flatMap(Collection::stream);

                            return Stream.concat(blockQuestions, variantQuestions);
                        }
                ).toList();
    }

    @Override
    public Map<UUID, QuestionDto> extractQuestionDtoMap(TestPatternEntity testPattern) {
        var questionEntities = extractQuestionEntities(testPattern);

        return questionEntities.stream()
                .collect(
                        HashMap::new,
                        (map, questionEntity) -> map.put(questionEntity.getId(), questionMapper.toDto(questionEntity)),
                        HashMap::putAll
                );
    }

    @Override
    public Question findQuestionById(List<Question> questions, UUID questionId) {
        return questions.stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Question %s not found".formatted(questionId)));
    }
}
