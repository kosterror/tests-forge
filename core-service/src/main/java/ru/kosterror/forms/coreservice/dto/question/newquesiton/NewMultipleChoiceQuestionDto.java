package ru.kosterror.forms.coreservice.dto.question.newquesiton;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewMultipleChoiceQuestionDto extends NewQuestionDto {

    @Schema(description = "List of options for the question", requiredMode = REQUIRED)
    @NotNull(message = "Options list is required")
    @Size(min = 2, message = "Options list must contain at least 2 elements")
    private List<String> options;

    @Schema(description = "List of indices of correct options", requiredMode = REQUIRED)
    @NotNull(message = "Correct option indices list is required")
    @Size(min = 1, message = "Correct option indices list must contain at least 1 element")
    private List<Integer> correctOptionIndices;

    public NewMultipleChoiceQuestionDto() {
        super(QuestionType.MULTIPLE_CHOICE);
    }
}
