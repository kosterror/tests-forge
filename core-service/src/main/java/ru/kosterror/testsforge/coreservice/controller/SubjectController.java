package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.coreservice.dto.subject.SubjectDto;
import ru.kosterror.testsforge.coreservice.dto.subject.UpdateSubjectDto;
import ru.kosterror.testsforge.coreservice.service.subject.SubjectService;

import java.util.List;
import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER_OR_STUDENT;

@Tag(name = "Subjects")
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @PreAuthorize(TEACHER)
    @Operation(summary = "Создать предмет", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public SubjectDto createSubject(@RequestBody @Valid UpdateSubjectDto subjectDto) {
        return service.createSubject(subjectDto);
    }

    @PreAuthorize(TEACHER_OR_STUDENT)
    @Operation(summary = "Получить предмет", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public SubjectDto getSubject(@PathVariable UUID id) {
        return service.getSubject(id);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Поиск предметов", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public List<SubjectDto> findSubjects(@RequestParam(required = false) String name) {
        return service.findSubjects(name);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Изменить предмет", security = @SecurityRequirement(name = JWT))
    @PostMapping("/{id}")
    public SubjectDto updateSubject(@PathVariable UUID id,
                                    @RequestBody @Valid UpdateSubjectDto subjectDto) {
        return service.updateSubject(id, subjectDto);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Удалить предмет", security = @SecurityRequirement(name = JWT))
    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable UUID id) {
        service.deleteSubject(id);
    }

}
