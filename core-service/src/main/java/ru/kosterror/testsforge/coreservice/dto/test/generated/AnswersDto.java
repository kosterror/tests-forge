package ru.kosterror.testsforge.coreservice.dto.test.generated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record AnswersDto(

        @NotNull(message = "Не может быть null, должно быть хотя бы пустым")
        @Schema(description = "Мапа для ответов на вопросы с единственным выбором. " +
                "Должна быть хотя бы пустой, но не null. Ключ - ID вопроса, значение - ID ответа",
                requiredMode = REQUIRED)
        Map<UUID, @NotNull UUID> singleChoiceAnswers,

        @NotNull(message = "Не может быть null, должно быть хотя бы пустым")
        @Schema(description = "Мапа для ответов на вопросы со множественным выбором. " +
                "Должна быть хотя бы пустой, но не null. Ключ - ID вопроса, значение - список ID ответов",
                requiredMode = REQUIRED)
        Map<UUID, @NotEmpty List<@NotNull UUID>> multipleChoiceAnswers,

        @NotNull(message = "Не может быть null, должно быть хотя бы пустым")
        @Schema(description = "Мапа для ответов на вопросы с текстовым вводом. " +
                "Должна быть хотя бы пустой, но не null. Ключ - ID вопроса, значение - текст ответа",
                requiredMode = REQUIRED)
        Map<UUID, @NotEmpty String> textAnswers,

        @NotNull(message = "Не может быть null, должно быть хотя бы пустым")
        @Schema(description = "Мапа для ответов на вопросы с сопоставлением. Ключ - ID вопроса, значение - список, " +
                "где элементы списка - пары. Первый элемент пары - ID термина, второй - ID определения",
                requiredMode = REQUIRED)
        Map<UUID, @NotEmpty List<Pair<@NotNull UUID, @NotNull UUID>>> matchingAnswers
) {
}
