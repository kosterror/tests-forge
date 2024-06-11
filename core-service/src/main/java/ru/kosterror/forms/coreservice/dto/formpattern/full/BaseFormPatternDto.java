package ru.kosterror.forms.coreservice.dto.formpattern.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kosterror.forms.coreservice.dto.subject.SubjectDto;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class BaseFormPatternDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Название", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Описание", requiredMode = NOT_REQUIRED)
    private String description;

    @Schema(description = "Идентификатор владельца", requiredMode = REQUIRED)
    private UUID ownerId;

    @Schema(description = "Предмет")
    private SubjectDto subject;

}
