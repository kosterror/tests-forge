package ru.kosterror.testsforge.coreservice.entity.test.generated;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
public class Variant {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Название варианта", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Описание варианта", requiredMode = NOT_REQUIRED)
    private String description;

    @Schema(description = "Вопросы", requiredMode = REQUIRED)
    private List<Question> questions;

}
