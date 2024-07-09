package ru.kosterror.testsforge.coreservice.dto.test.pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.kosterror.testsforge.coreservice.dto.question.update.UpdateQuestionDto;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UpdateBlockVariantDto(
        @NotNull(message = "Название варианта не может быть null")
        @Schema(description = "Название варианта", requiredMode = REQUIRED)
        String name,

        @Schema(description = "Описание варианта", requiredMode = NOT_REQUIRED)
        String description,

        @NotNull(message = "Список вопросов не может быть null")
        @Size(min = 1, message = "Список вопросов должен содержать хотя бы один элемент")
        @Schema(description = "Список вопросов", requiredMode = REQUIRED)
        List<@Valid UpdateQuestionDto> questions
) {
}
