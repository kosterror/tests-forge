package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.PartitionDto;
import ru.kosterror.testsforge.coreservice.service.partition.PartitionService;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tests/patterns/partitions")
public class PartitionController {

    private final PartitionService partitionService;

    @PreAuthorize(TEACHER)
    @Operation(summary = "Поиск по разделам", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public PaginationResponse<PartitionDto> getPartitions(@RequestParam String partitionName,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        return partitionService.getPartitions(partitionName, page, size);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Получить раздел по id", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public PartitionDto getPartition(@PathVariable UUID id) {
        return partitionService.getPartition(id);
    }


}
