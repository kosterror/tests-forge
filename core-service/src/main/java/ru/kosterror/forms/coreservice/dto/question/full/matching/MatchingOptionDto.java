package ru.kosterror.forms.coreservice.dto.question.full.matching;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
public class MatchingOptionDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Текст", requiredMode = REQUIRED)
    private String text;

}
