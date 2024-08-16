package ru.kosterror.testsforge.coreservice.dto.test.generated.verification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
public class VerificationGeneratedTest {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Статус", requiredMode = REQUIRED)
    private TestStatus status;

    @Schema(description = "ID студента", requiredMode = REQUIRED)
    private UUID userId;

    @Schema(description = "Разделы", requiredMode = REQUIRED)
    private List<VerificationPartition> partitions;

}
