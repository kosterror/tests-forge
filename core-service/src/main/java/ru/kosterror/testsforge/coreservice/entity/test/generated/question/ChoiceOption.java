package ru.kosterror.testsforge.coreservice.entity.test.generated.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class ChoiceOption {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Значение варианта ответа", requiredMode = REQUIRED)
    private String name;

}
