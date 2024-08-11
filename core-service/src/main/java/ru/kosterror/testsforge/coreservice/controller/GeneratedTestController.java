package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.service.GeneratedTestService;
import ru.kosterror.testsforge.securitystarter.model.JwtUser;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;

@Tag(name = "Generated Tests")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tests/published/{publishedTestId}/generated-tests/")
public class GeneratedTestController {

    private final GeneratedTestService generatedTestService;

    @Operation(summary = "Получить сгенерированный тест", security = @SecurityRequirement(name = JWT))
    @GetMapping("/my")
    public GeneratedTestDto getMyGeneratedTest(@AuthenticationPrincipal JwtUser principal,
                                               @PathVariable UUID publishedTestId) {
        return generatedTestService.getMyGeneratedTest(principal.userId(), publishedTestId);
    }

    @Operation(summary = "Сохранить ответы", security = @SecurityRequirement(name = JWT))
    @PostMapping("/{generatedTestId}/save")
    public GeneratedTestDto saveAnswers(@AuthenticationPrincipal JwtUser principal,
                                        @PathVariable UUID publishedTestId,
                                        @PathVariable UUID generatedTestId,
                                        @RequestBody @Valid AnswersDto answers) {
        return generatedTestService.saveAnswers(principal.userId(), publishedTestId, generatedTestId, answers);
    }

    @Operation(summary = "Сдать тест", security = @SecurityRequirement(name = JWT))
    @PostMapping("/{generatedTestId}/submit")
    public void submitTest(@AuthenticationPrincipal JwtUser principal,
                           @PathVariable UUID publishedTestId,
                           @PathVariable UUID generatedTestId,
                           @RequestBody @Valid AnswersDto answers) {
        generatedTestService.submitTest(principal.userId(), publishedTestId, generatedTestId, answers);
    }

}