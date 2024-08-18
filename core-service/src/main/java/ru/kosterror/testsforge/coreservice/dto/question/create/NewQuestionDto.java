package ru.kosterror.testsforge.coreservice.dto.question.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.question.NewQuestionType;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class NewQuestionDto extends CreateQuestionDto {

    @Schema(description = "Текст вопроса", requiredMode = REQUIRED)
    @NotNull(message = "Текст вопроса обязателен")
    private String name;

    @Schema(description = "Вложения к вопросу", requiredMode = NOT_REQUIRED)
    private List<UUID> attachments;

    protected NewQuestionDto(NewQuestionType type) {
        super(type);
    }

}
