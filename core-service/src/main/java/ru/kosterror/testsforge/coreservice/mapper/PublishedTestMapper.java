package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.PublishedTestEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface PublishedTestMapper {

    @Mapping(target = "testPatternId", source = "testPattern.id")
    BasePublishedTestDto toBaseDto(PublishedTestEntity entity);

}
