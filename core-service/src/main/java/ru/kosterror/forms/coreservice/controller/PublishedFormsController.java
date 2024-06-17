package ru.kosterror.forms.coreservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kosterror.forms.coreservice.dto.PublishFormDto;
import ru.kosterror.forms.coreservice.service.PublishedFormService;

import static ru.kosterror.forms.coreservice.configuration.OpenApiConfiguration.JWT;

@Tag(name = "Published forms")
@RestController
@RequestMapping("/api/forms/published")
@RequiredArgsConstructor
public class PublishedFormsController {

    private final PublishedFormService service;

    @Operation(summary = "Опубликовать форму", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public void publishForm(@RequestBody @Valid PublishFormDto publishFormDto) {
        service.publishForm(publishFormDto);
    }

}
