package ru.kosterror.testsforge.coreservice.service.processor.test.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Variant;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.service.processor.question.QuestionProcessor;
import ru.kosterror.testsforge.coreservice.service.processor.test.GeneratedTestProcessor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratedTestProcessorImpl implements GeneratedTestProcessor {

    private final List<QuestionProcessor> questionProcessors;

    @Override
    public final void markAnswers(GeneratedTestEntity generatedTest, AnswersDto answers) {
        log.info("Started processing generated test {} answers...", generatedTest.getId());

        var questions = extractQuestions(generatedTest);

        questionProcessors.forEach(questionProcessor -> questionProcessor.markQuestionAnswers(questions, answers));

        log.info("Generated test {} answers processed successfully", generatedTest.getId());
    }

    private List<Question> extractQuestions(GeneratedTestEntity generatedTest) {
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

}

