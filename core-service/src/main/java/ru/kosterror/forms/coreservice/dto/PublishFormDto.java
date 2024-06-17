package ru.kosterror.forms.coreservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.kosterror.forms.coreservice.util.validation.AtLeastOneId;
import ru.kosterror.forms.coreservice.util.validation.NotBeforeNow;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AtLeastOneId(message = "groupIds или userIds должен содержать хотя бы один элемент")
public class PublishFormDto {

    @NotNull(message = "Идентификатор шаблона формы не может быть null")
    @Schema(description = "Идентификатор шаблона формы", requiredMode = REQUIRED)
    private UUID formPatternId;

    @NotNull(message = "Множество идентификаторов групп не может быть null")
    @Schema(description = "Идентификаторы групп", requiredMode = REQUIRED)
    private Set<UUID> groupIds;

    @NotNull(message = "Множество идентификаторов пользователей не может быть null")
    @Schema(description = "Идентификаторы пользователей", requiredMode = REQUIRED)
    private Set<UUID> userIds;

    @Schema(description = "Дедлайн формы", requiredMode = NOT_REQUIRED)
    @NotBeforeNow(message = "Дедлайн не может быть в прошлом")
    private LocalDateTime deadline;

    @Positive(message = "Значение таймера должно быть позитивным или null")
    @Schema(description = "Время таймера в минутах", requiredMode = NOT_REQUIRED)
    private Integer timer;

}
