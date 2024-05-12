package ru.kosterror.forms.coreservice.dto.question.newquesiton;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewMatchingQuestionDto extends NewQuestionDto {

    @Schema(description = "Словарь, где ключ - термин, а значение - определение", requiredMode = REQUIRED)
    private Map<String, String> termsAndDefinitions;

    public NewMatchingQuestionDto() {
        super(QuestionType.MATCHING);
    }
}
