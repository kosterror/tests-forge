package ru.kosterror.testsforge.coreservice.mapper;

import org.mapstruct.*;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.MyGeneratedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.SubmittedTest;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;

import java.time.LocalDateTime;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = PublishedTestMapper.class
)
public abstract class GeneratedTestMapper {

    public abstract GeneratedTestDto toDto(GeneratedTestEntity entity);

    public abstract MyGeneratedTestDto toMyDto(GeneratedTestEntity entity);

    public abstract SubmittedTest toSubmittedTest(GeneratedTestEntity generatedTestEntity);

    @AfterMapping
    void hidePointsIfNeeds(@MappingTarget MyGeneratedTestDto dto) {
        if (!dto.getPublishedTest().isShowPointsToStudents()
                && dto.getPublishedTest().getDeadline().isAfter(LocalDateTime.now())) {
            dto.setPoints(null);
            dto.setMark(null);
        }
    }
}
