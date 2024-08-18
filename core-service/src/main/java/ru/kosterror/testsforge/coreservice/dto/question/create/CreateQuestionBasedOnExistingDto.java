package ru.kosterror.testsforge.coreservice.dto.question.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.question.NewQuestionType;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateQuestionBasedOnExistingDto extends CreateQuestionDto {

    @NotNull(message = "Идентификатор вопроса обязателен")
    @Schema(description = "Идентификатор вопроса", requiredMode = REQUIRED)
    private UUID questionId;

    protected CreateQuestionBasedOnExistingDto() {
        super(NewQuestionType.BASED_ON_EXISTING);
    }

}
