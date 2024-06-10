package ru.kosterror.forms.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.forms.coreservice.dto.formpattern.createupdate.CreateBlockVariantDto;
import ru.kosterror.forms.coreservice.dto.formpattern.full.VariantDto;
import ru.kosterror.forms.coreservice.entity.form.VariantEntity;
import ru.kosterror.forms.coreservice.mapper.question.QuestionMapper;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = QuestionMapper.class
)
public abstract class VariantMapper {

    public abstract List<VariantEntity> toEntities(List<CreateBlockVariantDto> dtos);

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
