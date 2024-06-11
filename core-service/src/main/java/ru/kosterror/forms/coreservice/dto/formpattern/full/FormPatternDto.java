package ru.kosterror.forms.coreservice.dto.formpattern.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@EqualsAndHashCode(callSuper = true)
public class FormPatternDto extends BaseFormPatternDto {

    @Schema(description = "Список разделов", requiredMode = REQUIRED)
    private List<PartitionDto> partitions;

}
