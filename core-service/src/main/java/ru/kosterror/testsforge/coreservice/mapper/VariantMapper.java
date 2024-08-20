package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.VariantDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.VariantEntity;
import ru.kosterror.testsforge.coreservice.factory.question.QuestionFactory;
import ru.kosterror.testsforge.coreservice.mapper.question.QuestionMapper;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {QuestionMapper.class, QuestionFactory.class}
)
public interface VariantMapper {

    List<VariantDto> toDtos(List<VariantEntity> entities);

}
