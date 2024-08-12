package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.BaseTestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.TestPatternDto;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.UpdateTestPatternDto;
import ru.kosterror.testsforge.coreservice.service.test.TestPatternService;
import ru.kosterror.testsforge.securitystarter.model.JwtUser;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;

@Tag(name = "Test patterns")
@RestController
@RequestMapping("/api/tests/patterns")
@RequiredArgsConstructor
public class TestPatternController {

    private final TestPatternService testPatternService;

    @Operation(summary = "Создать шаблон теста", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public TestPatternDto createTestPattern(@AuthenticationPrincipal JwtUser principal,
                                            @RequestBody @Valid UpdateTestPatternDto updateTestPatternDto) {
        return testPatternService.createTestPattern(principal.userId(), updateTestPatternDto);
    }

    @Operation(summary = "Получить шаблон теста", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public TestPatternDto getTestPattern(@PathVariable UUID id) {
        return testPatternService.getTestPattern(id);
    }

    @Operation(summary = "Получить шаблоны тестов с фильтрацией", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public PaginationResponse<BaseTestPatternDto> getTestPatterns(@Min(0) @RequestParam(defaultValue = "0") int page,
                                                                  @Min(1) @Max(200) @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) UUID ownerId,
                                                                  @RequestParam(required = false) UUID subjectId) {
        return testPatternService.getTestPatterns(page, size, name, ownerId, subjectId);
    }

}
