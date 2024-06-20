package ru.kosterror.testsforge.coreservice.dto.question.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.entity.question.QuestionType;

import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateMatchingQuestionDto extends UpdateQuestionDto {

    @Size(min = 2, max = 32, message = "Количество терминов и определений должно быть от 2 до 32")
    @Schema(description = "Словарь, где ключ - термин, а значение - определение", requiredMode = REQUIRED)
    private Map<String, String> termsAndDefinitions;

    @Size(min = 1, max = 33, message = "Количество пар должно быть от 1 до 33")
    @Schema(description = "Конфиг баллов. Ключ - верхняя граница количества правильных сопоставлений, " +
            "значение - соответствующий балл. Количество пар не может быть больше, чем количество терминов и " +
            "определений + 1")
    private Map<Integer, Integer> points;

    public UpdateMatchingQuestionDto() {
        super(QuestionType.MATCHING);
    }
}
