package ru.kosterror.forms.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.forms.coreservice.dto.question.newquesiton.NewQuestionDto;
import ru.kosterror.forms.coreservice.service.question.QuestionService;

import java.util.UUID;

import static ru.kosterror.forms.coreservice.configuration.SpringDocConfiguration.JWT;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@Tag(name = "Question")
public class QuestionController {

    private final QuestionService service;

    @Operation(summary = "Создать вопрос", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public QuestionDto createQuestion(@RequestBody @Valid NewQuestionDto question) {
        return service.createQuestion(question);
    }

    @Operation(summary = "Получить вопрос")
    @GetMapping("/{id}")
    public QuestionDto getQuestion(@PathVariable UUID id) {
        return service.getQuestion(id);
    }

}
