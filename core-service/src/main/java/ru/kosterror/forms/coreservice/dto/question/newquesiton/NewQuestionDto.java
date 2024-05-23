package ru.kosterror.forms.coreservice.dto.question.newquesiton;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
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

    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Question is required")
    private String title;

    @Schema(description = "Баллы за вопрос", requiredMode = REQUIRED)
    @Min(value = 1, message = "Points must be greater than 0")
    private int points;

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Type is required")
    private QuestionType type;

    @Schema(description = "Видимость вопроса в банке вопросов", requiredMode = NOT_REQUIRED, defaultValue = "false")
    private Boolean isVisible;

    @Schema(description = "Вложения к вопросу", requiredMode = NOT_REQUIRED)
    private List<UUID> attachments;

    protected NewQuestionDto(QuestionType type) {
        this.type = type;
    }
}
