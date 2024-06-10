package ru.kosterror.forms.coreservice.dto.question.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateMultipleChoiceQuestionDto extends UpdateQuestionDto {

    @Schema(description = "Список вариантов ответа", requiredMode = REQUIRED)
    @NotNull(message = "Список вариантов не может быть null")
    @Size(min = 1, max = 32, message = "Количество вариантов ответа должно быть от 1 до 32")
    private List<String> options;

    @Schema(description = "Список индексов правильных вариантов ответа", requiredMode = REQUIRED)
    @NotNull(message = "Список правильных вариантов не может быть null")
    @Size(min = 1, message = "Должен быть хотя бы один правильный ответ")
    private List<Integer> correctOptionIndices;

    @Size(min = 1, max = 33, message = "Количество пар должно быть от 1 до 33")
    @Schema(description = "Конфиг баллов. Ключ - верхняя граница количества правильных ответов, " +
            "значение - соответствующий балл. Количество пар не может быть больше, чем количество вариантов ответа + 1")
    private Map<Integer, Integer> points;

    public UpdateMultipleChoiceQuestionDto() {
        super(QuestionType.MULTIPLE_CHOICE);
    }
}
