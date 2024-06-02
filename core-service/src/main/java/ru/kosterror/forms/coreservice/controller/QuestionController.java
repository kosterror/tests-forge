package ru.kosterror.forms.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.forms.coreservice.dto.question.newquesiton.NewQuestionDto;
import ru.kosterror.forms.coreservice.service.question.QuestionService;
import ru.kosterror.forms.securitystarter.model.JwtUser;

import java.util.UUID;

import static ru.kosterror.forms.coreservice.configuration.SpringDocConfiguration.JWT;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Question")
public class QuestionController {

    private final QuestionService service;

    @Operation(summary = "Создать вопрос", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public QuestionDto createQuestion(@AuthenticationPrincipal JwtUser principal,
                                      @RequestBody @Valid NewQuestionDto question) {
        return service.createQuestion(principal.userId(), question);
    }

    @Operation(summary = "Получить вопрос")
    @GetMapping("/{id}")
    public QuestionDto getQuestion(@PathVariable UUID id) {
        return service.getQuestion(id);
    }

}
