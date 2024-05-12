package ru.kosterror.forms.coreservice.dto.question.full.matching;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
public class TermDefinitionDto {

    @Schema(description = "Термин", requiredMode = REQUIRED)
    private MatchingOptionDto term;

    @Schema(description = "Определение для термина", requiredMode = REQUIRED)
    private MatchingOptionDto definition;

}
