package ru.kosterror.forms.dto.question.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.kosterror.forms.entity.question.QuestionType;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public abstract class QuestionDto {

    @Schema(description = "Question id", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Question text", requiredMode = REQUIRED)
    private String question;

    @Schema(description = "Comment for the question", requiredMode = REQUIRED)
    private String comment;

    @Schema(description = "Points for the question", requiredMode = REQUIRED)
    private int points;

    @Schema(description = "Type of the question", requiredMode = REQUIRED)
    private QuestionType type;

    protected QuestionDto(QuestionType type) {
        this.type = type;
    }

}
