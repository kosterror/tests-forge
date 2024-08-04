package ru.kosterror.testsforge.coreservice.entity.test.generated.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SingleChoiceQuestion extends Question {

    @Schema(description = "Варианты ответов", requiredMode = REQUIRED)
    private List<ChoiceOption> options;

    @Schema(description = "Введенный ответ", requiredMode = NOT_REQUIRED)
    private Integer enteredAnswerIndex;

    public SingleChoiceQuestion(UUID id,
                                String name,
                                String description,
                                List<UUID> attachments,
                                List<ChoiceOption> options) {
        super(id, name, description, QuestionType.SINGLE_CHOICE, attachments, null);
        this.options = options;
    }

}
