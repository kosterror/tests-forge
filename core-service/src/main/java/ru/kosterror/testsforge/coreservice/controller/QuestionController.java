package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.service.question.QuestionService;
import ru.kosterror.testsforge.securitystarter.model.JwtUser;

import java.util.List;
import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Questions")
public class QuestionController {

    private final QuestionService service;

    @PreAuthorize(TEACHER)
    @Operation(summary = "Создать вопрос", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public QuestionDto createQuestion(@AuthenticationPrincipal JwtUser principal,
                                      @RequestParam UUID subjectId,
                                      @RequestBody @Valid CreateQuestionDto question
    ) {
        return service.createQuestion(principal.userId(), subjectId, question);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Удалить вопрос", security = @SecurityRequirement(name = JWT))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
        service.deleteQuestion(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Получить вопрос", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public QuestionDto getQuestion(@PathVariable UUID id) {
        return service.getQuestion(id);
    }

    @PreAuthorize(TEACHER)
    @Operation(summary = "Получить вопросы", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public PaginationResponse<QuestionDto> getQuestions(@RequestParam(required = false) UUID subjectId,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) List<QuestionType> types,
                                                        @RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "20") int size
    ) {
        return service.getQuestions(subjectId, name, types, page, size);
    }

}
