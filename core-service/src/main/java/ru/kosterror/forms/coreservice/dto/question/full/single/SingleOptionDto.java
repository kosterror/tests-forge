package ru.kosterror.forms.coreservice.dto.question.full.single;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class SingleOptionDto {

    @Schema(description = "Option id", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Option value", requiredMode = REQUIRED)
    private String value;

    @Schema(description = "Is the option right", requiredMode = REQUIRED)
    private boolean isRight;

}
