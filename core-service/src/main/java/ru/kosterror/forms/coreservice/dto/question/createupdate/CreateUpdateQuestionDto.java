package ru.kosterror.forms.coreservice.dto.question.createupdate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @JsonSubTypes.Type(value = CreateUpdateMultipleChoiceQuestionDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = CreateUpdateSingleChoiceQuestionDto.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = CreateUpdateMatchingQuestionDto.class, name = "MATCHING"),
        @JsonSubTypes.Type(value = CreateUpdateTextInputQuestionDto.class, name = "TEXT_INPUT"),
})
@Data
public abstract class CreateUpdateQuestionDto {
    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Текст вопроса обязателен")
    private String name;

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Тип вопроса обязателен")
    private QuestionType type;

    @Schema(description = "Вложения к вопросу", requiredMode = NOT_REQUIRED)
    private List<UUID> attachments;

    protected CreateUpdateQuestionDto(QuestionType type) {
        this.type = type;
    }
}
