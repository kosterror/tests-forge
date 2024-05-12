package ru.kosterror.forms.coreservice.dto.question.full.single;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class SingleOptionDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Значение варианта ответа", requiredMode = REQUIRED)
    private String value;

    @Schema(description = "Является ли вариант ответа правильным", requiredMode = REQUIRED)
    private boolean isRight;

}
