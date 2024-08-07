package ru.kosterror.testsforge.coreservice.entity.test.generated.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = SingleChoiceQuestion.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = MathcingQuestion.class, name = "MATCHING"),
        @JsonSubTypes.Type(value = TextInputQuestion.class, name = "TEXT_INPUT"),
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Question {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Тип вопроса", requiredMode = REQUIRED)
    private QuestionType type;

    @Schema(description = "Прикрепленные файлы", requiredMode = NOT_REQUIRED)
    private List<UUID> attachments;

    @Schema(description = "Полученное количество баллов", requiredMode = REQUIRED)
    private Integer points;

}
