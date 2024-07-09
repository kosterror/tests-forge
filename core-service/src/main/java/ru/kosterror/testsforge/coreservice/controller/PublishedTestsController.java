package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishTestDto;
import ru.kosterror.testsforge.coreservice.service.PublishedTestService;

import java.util.UUID;

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

    @Operation(summary = "Поиск по опубликованным формам с пагинацией", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public PaginationResponse<BasePublishedTestDto> getPublishedTests(@RequestParam(required = false) String name,
                                                                      @RequestParam(required = false) UUID subjectId,
                                                                      @RequestParam(required = false) UUID groupId,
                                                                      @RequestParam int page,
                                                                      @RequestParam int size) {
        return service.getPublishedTests(name, subjectId, groupId, page, size);
    }

}
