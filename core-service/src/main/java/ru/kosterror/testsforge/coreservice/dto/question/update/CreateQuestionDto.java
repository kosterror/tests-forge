package ru.kosterror.testsforge.coreservice.dto.question.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionType;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateMultipleChoiceQuestionDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = CreateSingleChoiceQuestionDto.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = CreateMatchingQuestionDto.class, name = "MATCHING"),
        @JsonSubTypes.Type(value = CreateTextInputQuestionDto.class, name = "TEXT_INPUT"),
})
@Data
public abstract class CreateQuestionDto {
    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Текст вопроса обязателен")
    private String name;

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Тип вопроса обязателен")
    private QuestionType type;

    @Schema(description = "Вложения к вопросу", requiredMode = NOT_REQUIRED)
    private List<UUID> attachments;

    protected CreateQuestionDto(QuestionType type) {
        this.type = type;
    }
}
