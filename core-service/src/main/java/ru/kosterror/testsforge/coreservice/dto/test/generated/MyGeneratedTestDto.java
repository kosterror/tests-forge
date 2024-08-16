package ru.kosterror.testsforge.coreservice.dto.test.generated;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class MyGeneratedTestDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    UUID id;

    @Schema(description = "Статус", requiredMode = REQUIRED)
    TestStatus status;

    @Schema(description = "Опубликованный тест", requiredMode = REQUIRED)
    BasePublishedTestDto publishedTest;

    @Schema(description = "Момент сдачи теста", requiredMode = NOT_REQUIRED)
    LocalDateTime submitDateTime;

    @Schema(description = "Полученные баллы", requiredMode = NOT_REQUIRED)
    Integer points;

}
