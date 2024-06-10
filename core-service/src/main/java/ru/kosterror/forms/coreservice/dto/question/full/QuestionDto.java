package ru.kosterror.forms.coreservice.dto.question.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public abstract class QuestionDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Идентификатор владельца", requiredMode = REQUIRED)
    private UUID ownerId;

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    private QuestionType type;

    @Schema(description = "Прикрепленные файлы", requiredMode = REQUIRED)
    private List<UUID> attachments;
    
    protected QuestionDto(QuestionType type) {
        this.type = type;
    }

}
