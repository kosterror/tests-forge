package ru.kosterror.testsforge.coreservice.entity.test.generated.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TextInputQuestion extends Question {

    public TextInputQuestion(UUID id,
                             String name,
                             List<UUID> attachments) {
        super(id, name, QuestionType.TEXT_INPUT, attachments, null);
    }
}
