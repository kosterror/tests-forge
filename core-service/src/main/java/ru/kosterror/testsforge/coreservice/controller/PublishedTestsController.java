package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishTestDto;
import ru.kosterror.testsforge.coreservice.service.PublishedTestService;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;

@Tag(name = "Published tests")
@RestController
@RequestMapping("/api/tests/published")
@RequiredArgsConstructor
public class PublishedTestsController {

    private final PublishedTestService service;

    @Operation(summary = "Опубликовать тест", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public BasePublishedTestDto publishTest(@RequestBody @Valid PublishTestDto publishTestDto) {
        return service.publishTest(publishTestDto);
    }

}
