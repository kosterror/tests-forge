package ru.kosterror.testsforge.coreservice.dto.test.generated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.util.Pair;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestStatus;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CheckTestDto(

        @NotNull(message = "Статус не может быть null")
        @Schema(description = "Новый статус теста", requiredMode = REQUIRED)
        GeneratedTestStatus status,

        @Schema(description = "Измененные баллы за вопросы. Может быть null или не пустым",
                requiredMode = NOT_REQUIRED
        )
        List<Pair<UUID, Integer>> points
) {
}
