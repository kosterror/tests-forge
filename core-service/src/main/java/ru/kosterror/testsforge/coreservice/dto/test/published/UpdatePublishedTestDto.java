package ru.kosterror.testsforge.coreservice.dto.test.published;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.kosterror.testsforge.coreservice.util.validation.AtLeastOneId;
import ru.kosterror.testsforge.coreservice.util.validation.NotBeforeNow;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@AtLeastOneId(message = "groupIds или userIds должен содержать хотя бы один элемент")
public record UpdatePublishedTestDto(

        @Schema(description = "Дедлайн", requiredMode = NOT_REQUIRED)
        @NotBeforeNow(message = "Дедлайн не может быть в прошлом")
        LocalDateTime deadline,

        @Positive(message = "Значение таймера должно быть позитивным или null")
        @Schema(description = "Время таймера в минутах", requiredMode = NOT_REQUIRED)
        Integer timer,

        @Size(max = 10, message = "Количество групп не может быть больше 10")
        @NotNull(message = "Множество идентификаторов групп не может быть null")
        @Schema(description = "Идентификаторы групп", requiredMode = REQUIRED)
        Set<UUID> groupIds,

        @Size(max = 50, message = "Количество пользователей не может быть больше 50")
        @NotNull(message = "Множество идентификаторов пользователей не может быть null")
        @Schema(description = "Идентификаторы пользователей", requiredMode = REQUIRED)
        Set<UUID> userIds,

        @NotNull(message = "Показывать ли баллы студентам не может быть null")
        @Schema(description = "Показывать ли баллы студентам", requiredMode = REQUIRED)
        Boolean showPointsToStudents,

        @NotNull(message = "Конфигурация оценок не может быть null")
        @Schema(description = "Конфигурация оценок в виде мапы. " +
                "Ключ - минимальное количество баллов для оценки, " +
                "значение - соответствующая оценка",
                requiredMode = REQUIRED)
        Map<Integer, String> markConfiguration

) {
}
