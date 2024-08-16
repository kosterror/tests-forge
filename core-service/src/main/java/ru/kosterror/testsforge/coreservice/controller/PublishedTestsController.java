package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.UpdatePublishedTestDto;
import ru.kosterror.testsforge.coreservice.service.test.PublishedTestService;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER_OR_STUDENT;

@Tag(name = "Published tests")
@RestController
@RequestMapping("/api/tests/published")
@RequiredArgsConstructor
public class PublishedTestsController {

    private final PublishedTestService service;

    @PreAuthorize(TEACHER)
    @Operation(summary = "Опубликовать тест", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public BasePublishedTestDto publishTest(@RequestBody @Valid PublishTestDto publishTestDto) {
        return service.publishTest(publishTestDto);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Изменить опубликованный тест", security = @SecurityRequirement(name = JWT))
    @PutMapping("/{id}")
    public BasePublishedTestDto updatePublishedTest(@PathVariable UUID id,
                                                    @RequestBody @Valid UpdatePublishedTestDto updatePublishedTestDto
    ) {
        return service.updatePublishedTest(id, updatePublishedTestDto);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Удалить опубликованный тест", security = @SecurityRequirement(name = JWT))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublishedTest(@PathVariable UUID id) {
        service.deletePublishedTest(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize(TEACHER_OR_STUDENT)
    @Operation(summary = "Получить опубликованные тесты", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public PaginationResponse<BasePublishedTestDto> getPublishedTests(@RequestParam(required = false) String name,
                                                                      @RequestParam(required = false) UUID subjectId,
                                                                      @RequestParam(required = false) UUID groupId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        return service.getPublishedTests(name, subjectId, groupId, page, size);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Получить опубликованный тест по id", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public PublishedTestDto getPublishedTest(@PathVariable UUID id) {
        return service.getPublishedTest(id);
    }

}
