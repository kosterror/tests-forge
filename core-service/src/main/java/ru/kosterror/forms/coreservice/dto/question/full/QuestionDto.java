package ru.kosterror.forms.coreservice.dto.question.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public abstract class QuestionDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    private String question;

    @Schema(description = "Комментарий для вопроса", requiredMode = REQUIRED)
    private String comment;

    @Schema(description = "Баллы за вопрос", requiredMode = REQUIRED)
    private int points;

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    private QuestionType type;

    protected QuestionDto(QuestionType type) {
        this.type = type;
    }

}
