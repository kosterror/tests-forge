package ru.kosterror.testsforge.coreservice.dto.test.generated;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Partition;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class GeneratedTestDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Статус", requiredMode = REQUIRED)
    private GeneratedTestStatus status;

    @Schema(description = "Опубликованный тест", requiredMode = REQUIRED)
    private BasePublishedTestDto publishedTest;

    @Schema(description = "ID студента", requiredMode = REQUIRED)
    private UUID userId;

    @Schema(description = "Разделы", requiredMode = REQUIRED)
    private List<Partition> partitions;

}
