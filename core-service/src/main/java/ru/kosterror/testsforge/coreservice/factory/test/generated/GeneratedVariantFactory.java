package ru.kosterror.testsforge.coreservice.factory.test.generated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Variant;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.VariantEntity;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class GeneratedVariantFactory {

    private final Random random;
    private final GeneratedQuestionFactory generatedQuestionFactory;

    public Variant buildVariant(List<VariantEntity> variants) {
        var variantIndex = random.nextInt(variants.size());
        var variant = variants.get(variantIndex);

        return new Variant(variant.getId(),
                variant.getName(),
                variant.getDescription(),
                generatedQuestionFactory.buildQuestions(variant.getQuestions())
        );
    }

}
