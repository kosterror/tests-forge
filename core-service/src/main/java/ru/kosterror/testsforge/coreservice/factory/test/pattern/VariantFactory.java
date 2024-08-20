package ru.kosterror.testsforge.coreservice.factory.test.pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.variant.UpdateVariantDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.VariantEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.StaticBlockEntity;
import ru.kosterror.testsforge.coreservice.factory.question.QuestionFactory;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VariantFactory {

    private final QuestionFactory questionFactory;

    public List<VariantEntity> buildVariantEntitiesFromDtos(List<UpdateVariantDto> variantDtos,
                                                            StaticBlockEntity blockEntity) {
        return variantDtos.stream()
                .map(variantDto -> buildVariantEntityFromDto(variantDto, blockEntity))
                .toList();
    }

    public List<VariantEntity> buildVariantEntitiesFromEntities(List<VariantEntity> variantEntities,
                                                                StaticBlockEntity newBlockEntity) {
        return variantEntities.stream()
                .map(variantEntity -> buildVariantEntityFromEntity(variantEntity, newBlockEntity))
                .toList();
    }

    private VariantEntity buildVariantEntityFromDto(UpdateVariantDto variantDto, StaticBlockEntity blockEntity) {
        var variantEntity = new VariantEntity();

        variantEntity.setName(variantDto.name());
        variantEntity.setDescription(variantDto.description());
        variantEntity.setQuestions(questionFactory.buildQuestionsForVariantFromDtos(variantDto.questions(), variantEntity));
        variantEntity.setBlock(blockEntity);

        return variantEntity;
    }

    private VariantEntity buildVariantEntityFromEntity(VariantEntity variantEntity,
                                                       StaticBlockEntity newBlockEntity
    ) {
        var newVariantEntity = new VariantEntity();
        var newQuestions = questionFactory.buildQuestionsForVariantFromEntities(
                variantEntity.getQuestions(),
                newVariantEntity
        );

        newVariantEntity.setName(variantEntity.getName());
        newVariantEntity.setDescription(variantEntity.getDescription());
        newVariantEntity.setQuestions(newQuestions);
        newVariantEntity.setBlock(newBlockEntity);

        return variantEntity;
    }


}
