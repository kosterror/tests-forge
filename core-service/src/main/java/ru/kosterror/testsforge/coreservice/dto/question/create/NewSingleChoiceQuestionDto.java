package ru.kosterror.testsforge.coreservice.dto.question.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.question.NewQuestionType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewSingleChoiceQuestionDto extends NewQuestionDto {

    @Schema(description = "Список вариантов ответа", requiredMode = REQUIRED)
    @NotNull(message = "Список вариантов обязателен")
    @Size(min = 2, max = 32, message = "Количество вариантов ответа должно быть от 2 до 32")
    private List<String> options;

    @Schema(description = "Индекс правильного варианта ответа", requiredMode = REQUIRED)
    @NotNull(message = "Индекс правильного варианта ответа обязателен")
    private Integer correctOptionIndex;

    @Schema(description = "Количество баллов", requiredMode = REQUIRED)
    @NotNull(message = "Количество баллов обязательно")
    private Integer points;

    public NewSingleChoiceQuestionDto() {
        super(NewQuestionType.SINGLE_CHOICE);
    }
}
