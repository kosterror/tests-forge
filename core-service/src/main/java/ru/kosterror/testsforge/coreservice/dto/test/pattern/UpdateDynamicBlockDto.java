package ru.kosterror.testsforge.coreservice.dto.test.pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.question.update.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.entity.BlockType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateDynamicBlockDto extends UpdateBlockDto {

    @NotNull(message = "Список вопросов не может быть null")
    @Size(min = 1, message = "Список вопросов должен содержать хотя бы один элемент")
    @Schema(description = "Список вопросов", requiredMode = REQUIRED)
    List<@Valid CreateQuestionDto> questions;

    protected UpdateDynamicBlockDto() {
        super(BlockType.DYNAMIC);
    }

}
