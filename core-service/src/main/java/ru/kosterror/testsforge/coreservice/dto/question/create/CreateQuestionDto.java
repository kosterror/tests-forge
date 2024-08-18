package ru.kosterror.testsforge.coreservice.dto.question.create;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.dto.question.NewQuestionType;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewMultipleChoiceQuestionDto.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = NewSingleChoiceQuestionDto.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = NewMatchingQuestionDto.class, name = "MATCHING"),
        @JsonSubTypes.Type(value = NewTextInputQuestionDto.class, name = "TEXT_INPUT"),
        @JsonSubTypes.Type(value = CreateQuestionBasedOnExistingDto.class, name = "BASED_ON_EXISTING")
})
@Data
public abstract class CreateQuestionDto {

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Тип вопроса обязателен")
    private NewQuestionType type;

    protected CreateQuestionDto(NewQuestionType type) {
        this.type = type;
    }
}
