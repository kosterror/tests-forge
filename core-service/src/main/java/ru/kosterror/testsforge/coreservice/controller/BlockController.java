package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.full.BlockDto;
import ru.kosterror.testsforge.coreservice.service.block.BlockService;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tests/patterns/blocks")
public class BlockController {

    private final BlockService blockService;

    @PreAuthorize(TEACHER)
    @Operation(summary = "Поиск по блокам", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public PaginationResponse<BlockDto> getBlocks(@RequestParam String name,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        return blockService.getBlocks(name, page, size);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Получить блок по id", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public BlockDto getBlock(@PathVariable UUID id) {
        return blockService.getBlock(id);
    }

}
