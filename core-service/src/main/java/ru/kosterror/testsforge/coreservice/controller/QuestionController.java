package ru.kosterror.testsforge.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionDto;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.service.QuestionService;
import ru.kosterror.testsforge.securitystarter.model.JwtUser;

import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.configuration.OpenApiConfiguration.JWT;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Question")
public class QuestionController {

    private final QuestionService service;

    @Operation(summary = "Создать вопрос", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public QuestionDto createQuestion(@AuthenticationPrincipal JwtUser principal,
                                      @RequestParam UUID subjectId,
                                      @RequestBody @Valid CreateQuestionDto question
    ) {
        return service.createQuestion(principal.userId(), subjectId, question);
    }

    @Operation(summary = "Получить вопрос")
    @GetMapping("/{id}")
    public QuestionDto getQuestion(@PathVariable UUID id) {
        return service.getQuestion(id);
    }

}
