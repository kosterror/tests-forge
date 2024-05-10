package ru.kosterror.forms.dto.question.newquesiton;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.kosterror.forms.entity.question.QuestionType;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewMultipleChoiceQuestionDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = NewSingleChoiceQuestionDto.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = NewMatchingQuestionDto.class, name = "MATCHING"),
        @JsonSubTypes.Type(value = NewTextInputQuestionDto.class, name = "TEXT_INPUT"),
})
@Data
public abstract class NewQuestionDto {

    @Schema(description = "Question text", requiredMode = REQUIRED)
    @NotNull(message = "Question is required")
    private String question;

    @Schema(description = "Comment for the question", requiredMode = REQUIRED)
    @NotNull(message = "Comment is required")
    private String comment;

    @Schema(description = "Points for the question", requiredMode = REQUIRED)
    @Min(value = 1, message = "Points must be greater than 0")
    private int points;

    @Schema(description = "Type of the question", requiredMode = REQUIRED)
    @NotNull(message = "Type is required")
    private QuestionType type;

    protected NewQuestionDto(QuestionType type) {
        this.type = type;
    }
}
