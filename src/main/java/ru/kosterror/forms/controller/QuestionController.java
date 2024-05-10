package ru.kosterror.forms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.dto.question.newquesiton.NewQuestionDto;
import ru.kosterror.forms.service.question.QuestionService;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@Tag(name = "Question")
public class QuestionController {

    private final QuestionService service;

    @Operation(summary = "Create question")
    @PostMapping
    public QuestionDto createQuestion(@RequestBody @Valid NewQuestionDto question) {
        return service.createQuestion(question);
    }

}
