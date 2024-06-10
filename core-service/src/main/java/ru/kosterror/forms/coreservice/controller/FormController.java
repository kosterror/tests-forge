package ru.kosterror.forms.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.coreservice.dto.form.createupdate.CreateFormDto;
import ru.kosterror.forms.coreservice.dto.form.full.FormDto;
import ru.kosterror.forms.coreservice.service.FormService;
import ru.kosterror.forms.securitystarter.model.JwtUser;

import java.util.UUID;

import static ru.kosterror.forms.coreservice.configuration.OpenApiConfiguration.JWT;

@Tag(name = "Forms")
@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @Operation(summary = "Создать шаблон формы", security = @SecurityRequirement(name = JWT))
    @PostMapping("/patterns")
    public FormDto createFormPattern(@AuthenticationPrincipal JwtUser principal,
                                     @RequestBody @Valid CreateFormDto createFormDto) {
        return formService.createFormPattern(principal.userId(), createFormDto);
    }

    @Operation(summary = "Получить шаблон формы", security = @SecurityRequirement(name = JWT))
    @GetMapping("/patterns/{id}")
    public FormDto getFormPattern(@PathVariable UUID id) {
        return formService.getFormPattern(id);
    }

}
