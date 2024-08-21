package ru.kosterror.testsforge.coreservice.dto.test.generated;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record SubmittedTest(

        @Schema(description = "Статус теста", requiredMode = REQUIRED)
        TestStatus status,

        @Schema(description = "ID пользователя", requiredMode = REQUIRED)
        UUID userId,

        @Schema(description = "Баллы за тест", requiredMode = NOT_REQUIRED)
        Integer points,

        @Schema(description = "Оценка", requiredMode = NOT_REQUIRED)
        String mark,

        @Schema(description = "Дата и время сдачи теста", requiredMode = REQUIRED)
        LocalDateTime submitDateTime
) {
}
