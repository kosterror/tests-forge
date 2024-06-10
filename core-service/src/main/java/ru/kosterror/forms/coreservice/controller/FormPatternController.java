package ru.kosterror.forms.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.coreservice.dto.formpattern.full.FormPatternDto;
import ru.kosterror.forms.coreservice.dto.formpattern.update.UpdateFormPatternDto;
import ru.kosterror.forms.coreservice.service.FormPatternService;
import ru.kosterror.forms.securitystarter.model.JwtUser;

import java.util.UUID;

import static ru.kosterror.forms.coreservice.configuration.OpenApiConfiguration.JWT;

@Tag(name = "Forms")
@RestController
@RequestMapping("/api/forms/patterns")
@RequiredArgsConstructor
public class FormPatternController {

    private final FormPatternService formPatternService;

    @Operation(summary = "Создать шаблон формы", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public FormPatternDto createFormPattern(@AuthenticationPrincipal JwtUser principal,
                                            @RequestBody @Valid UpdateFormPatternDto updateFormPatternDto) {
        return formPatternService.createFormPattern(principal.userId(), updateFormPatternDto);
    }

    @Operation(summary = "Получить шаблон формы", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public FormPatternDto getFormPattern(@PathVariable UUID id) {
        return formPatternService.getFormPattern(id);
    }

}
