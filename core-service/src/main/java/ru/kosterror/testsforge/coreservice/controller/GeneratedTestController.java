package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}