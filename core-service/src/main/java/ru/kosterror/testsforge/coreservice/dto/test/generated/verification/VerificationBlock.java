package ru.kosterror.testsforge.coreservice.dto.test.generated.verification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockType;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
public class VerificationBlock {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Тип блока", requiredMode = REQUIRED)
    private BlockType type;

    @Schema(description = "Название блока", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Описание блока", requiredMode = NOT_REQUIRED)
    private String description;

    @Schema(description = "Вариант. Заполнено, если type = STATIC", requiredMode = NOT_REQUIRED)
    private VerificationVariant variant;

    @Schema(description = "Вопросы. Заполнено, если type = DYNAMIC", requiredMode = NOT_REQUIRED)
    private List<VerificationQuestion> verificationQuestions;

}
