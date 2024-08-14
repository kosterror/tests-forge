package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.MyGeneratedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.SubmittedTest;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestStatus;
import ru.kosterror.testsforge.coreservice.service.test.GeneratedTestService;
import ru.kosterror.testsforge.securitystarter.model.JwtUser;

import java.util.List;
import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;

@Tag(name = "Generated tests")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tests/generated")
public class GeneratedTestController {

    private final GeneratedTestService generatedTestService;

    @Operation(summary = "Получить сгенерированный тест", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public GeneratedTestDto getMyGeneratedTest(@AuthenticationPrincipal JwtUser principal,
                                               @RequestParam UUID publishedTestId) {
        return generatedTestService.getMyGeneratedTest(principal.userId(), publishedTestId);
    }

    @Operation(summary = "Сохранить ответы", security = @SecurityRequirement(name = JWT))
    @PostMapping("/{generatedTestId}/save")
    public GeneratedTestDto saveAnswers(@AuthenticationPrincipal JwtUser principal,
                                        @PathVariable UUID generatedTestId,
                                        @RequestParam UUID publishedTestId,
                                        @RequestBody @Valid AnswersDto answers) {
        return generatedTestService.saveAnswers(principal.userId(), publishedTestId, generatedTestId, answers);
    }

    @Operation(summary = "Сдать тест", security = @SecurityRequirement(name = JWT))
    @PostMapping("/{generatedTestId}/submit")
    public MyGeneratedTestDto submitTest(@AuthenticationPrincipal JwtUser principal,
                                         @PathVariable UUID generatedTestId,
                                         @RequestParam UUID publishedTestId,
                                         @RequestBody @Valid AnswersDto answers) {
        return generatedTestService.submitTest(principal.userId(), publishedTestId, generatedTestId, answers);
    }

    @Operation(summary = "Получить мои сгенерированные тесты", security = @SecurityRequirement(name = JWT))
    @GetMapping("/all")
    public PaginationResponse<MyGeneratedTestDto> getMyGeneratedTests(
            @AuthenticationPrincipal JwtUser principal,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return generatedTestService.getMyGeneratedTests(principal.userId(), subjectId, page, size);
    }

    @Operation(summary = "Получить сданные тесты", security = @SecurityRequirement(name = JWT))
    @GetMapping("/submitted")
    public PaginationResponse<SubmittedTest> getSubmittedTests(
            @RequestParam UUID publishedTestId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) GeneratedTestStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return generatedTestService.getSubmittedTests(userId, publishedTestId, status, page, size);
    }

    @Operation(summary = "Получить ID пользователей, не открывших тест", security = @SecurityRequirement(name = JWT))
    @GetMapping("/unsubmitted")
    public List<UUID> getUserIdsWithUnsubmittedTests(@RequestParam UUID publishedTestId) {
        return generatedTestService.getUserIdsWithUnsubmittedTests(publishedTestId);
    }

}