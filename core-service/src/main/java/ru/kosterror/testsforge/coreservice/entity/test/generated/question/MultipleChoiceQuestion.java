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
public class MultipleChoiceQuestion extends Question {

    @Schema(description = "Варианты ответов", requiredMode = REQUIRED)
    private List<ChoiceOption> options;

    @Schema(description = "Введенные ответы. Элемент списка - ID элемента из options", requiredMode = NOT_REQUIRED)
    private List<UUID> enteredAnswers;

    public MultipleChoiceQuestion(UUID id,
                                  String name,
                                  List<UUID> attachments,
                                  List<ChoiceOption> options) {
        super(id, name, QuestionType.MULTIPLE_CHOICE, attachments, null);
        this.options = options;
    }
}
