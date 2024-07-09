package ru.kosterror.testsforge.coreservice.dto.test.published;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.TestPatternDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class PublishedTestDto {

    @Schema(description = "ID опубликованного теста", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Дедлайн", requiredMode = NOT_REQUIRED)
    private LocalDateTime deadline;

    @Schema(description = "Таймер в минутах", requiredMode = NOT_REQUIRED)
    private Integer timer;

    @Schema(description = "Список ID групп", requiredMode = NOT_REQUIRED)
    private List<UUID> groupIds;

    @Schema(description = "Список ID пользователей", requiredMode = NOT_REQUIRED)
    private List<UUID> userIds;

    @Schema(description = "Шаблон теста", requiredMode = REQUIRED)
    private TestPatternDto testPattern;

}
