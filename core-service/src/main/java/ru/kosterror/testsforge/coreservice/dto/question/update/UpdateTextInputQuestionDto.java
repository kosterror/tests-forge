package ru.kosterror.testsforge.coreservice.dto.question.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateTextInputQuestionDto extends UpdateQuestionDto {

    @Schema(description = "Является ли ответ чувствительным к регистру", requiredMode = REQUIRED)
    @NotNull(message = "Чувствительность к регистру обязательна")
    private boolean isCaseSensitive;

    @Schema(description = "Список правильных ответов", requiredMode = REQUIRED)
    @NotNull(message = "Список ответов не может быть null")
    @Size(min = 1, message = "Должен быть хотя бы один правильный ответ")
    private List<String> answers;

    @Schema(description = "Количество баллов", requiredMode = REQUIRED)
    @NotNull(message = "Количество баллов обязательно")
    private Integer points;

    public UpdateTextInputQuestionDto() {
        super(QuestionType.TEXT_INPUT);
    }
}
