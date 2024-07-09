package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdateBlockVariantDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.VariantDto;
import ru.kosterror.testsforge.coreservice.entity.test.VariantEntity;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = QuestionMapper.class
)
public abstract class VariantMapper {

    public abstract List<VariantEntity> toEntities(List<UpdateBlockVariantDto> dtos);

    public abstract List<VariantDto> toDtos(List<VariantEntity> entities);

    @AfterMapping
    void addVariantToQuestion(@MappingTarget List<VariantEntity> variantEntities) {
        if (variantEntities != null) {
            for (var variant : variantEntities) {
                if (variant.getQuestions() != null) {
                    for (var question : variant.getQuestions()) {
                        question.setVariant(variant);
                    }
                }
            }
        }
    }

    @AfterMapping
    void orderQuestions(@MappingTarget List<VariantEntity> variantEntities) {
        if (variantEntities != null) {
            for (var variant : variantEntities) {
                if (variant.getQuestions() != null) {
                    for (int i = 0; i < variant.getQuestions().size(); i++) {
                        variant.getQuestions().get(i).setOrder(i);
                    }
                }
            }
        }
    }

}
