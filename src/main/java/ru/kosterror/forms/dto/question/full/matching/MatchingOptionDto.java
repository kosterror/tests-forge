package ru.kosterror.forms.dto.question.full.matching;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
public class MatchingOptionDto {

    @Schema(description = "Id", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Text", requiredMode = REQUIRED)
    private String text;

}
