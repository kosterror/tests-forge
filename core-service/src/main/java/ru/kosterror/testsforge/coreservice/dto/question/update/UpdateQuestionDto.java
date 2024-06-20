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
        @JsonSubTypes.Type(value = UpdateMultipleChoiceQuestionDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = UpdateSingleChoiceQuestionDto.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = UpdateMatchingQuestionDto.class, name = "MATCHING"),
        @JsonSubTypes.Type(value = UpdateTextInputQuestionDto.class, name = "TEXT_INPUT"),
})
@Data
public abstract class UpdateQuestionDto {
    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Текст вопроса обязателен")
    private String name;

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Тип вопроса обязателен")
    private QuestionType type;

    @Schema(description = "Вложения к вопросу", requiredMode = NOT_REQUIRED)
    private List<UUID> attachments;

    protected UpdateQuestionDto(QuestionType type) {
        this.type = type;
    }
}
