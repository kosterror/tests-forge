package ru.kosterror.testsforge.coreservice.dto.test.pattern.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@EqualsAndHashCode(callSuper = true)
public class TestPatternDto extends BaseTestPatternDto {

    @Schema(description = "Список разделов", requiredMode = REQUIRED)
    private List<PartitionDto> partitions;

}
