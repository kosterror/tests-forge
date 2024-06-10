package ru.kosterror.forms.coreservice.dto.question.full.multiple;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class MultipleOptionDto {

    @Schema(description = "Идентификатор варианта ответа", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Текст варианта ответа", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Является ли вариант ответа правильным", requiredMode = REQUIRED)
    private boolean isRight;

}
