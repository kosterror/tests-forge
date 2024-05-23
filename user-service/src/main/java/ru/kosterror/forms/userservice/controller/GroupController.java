package ru.kosterror.forms.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.commonmodel.PaginationResponse;
import ru.kosterror.forms.securitystarter.model.JwtUser;
import ru.kosterror.forms.userservice.dto.GroupDto;
import ru.kosterror.forms.userservice.dto.NewGroupDto;
import ru.kosterror.forms.userservice.service.GroupService;

import java.util.List;
import java.util.UUID;

import static ru.kosterror.forms.userservice.configuration.SpringDocConfiguration.JWT;

@Tag(name = "Groups")
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService service;

    @Operation(summary = "Создать новую группу", security = @SecurityRequirement(name = JWT))
    @PostMapping
    public GroupDto createGroup(@RequestBody @Valid NewGroupDto request) {
        return service.createGroup(request);
    }

    @Operation(summary = "Получить группу по id", security = @SecurityRequirement(name = JWT))
    @GetMapping("/{id}")
    public GroupDto getGroup(@PathVariable UUID id) {
        return service.getGroup(id);
    }

    @Operation(summary = "Получить группы пользователя", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public List<GroupDto> getUserGroups(@AuthenticationPrincipal JwtUser principal) {
        return service.getUserGroups(principal.userId());
    }

    @Operation(summary = "Изменить группу", security = @SecurityRequirement(name = JWT))
    @PutMapping("/{id}")
    public GroupDto updateGroup(@PathVariable UUID id, @RequestBody NewGroupDto groupDto) {
        return service.updateGroup(id, groupDto);
    }

    @Operation(summary = "Удалить группу по id", security = @SecurityRequirement(name = JWT))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable UUID id) {
        service.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Поиск по группам", security = @SecurityRequirement(name = JWT))
    @GetMapping("/search")
    public PaginationResponse<GroupDto> getGroups(@Parameter(description = "Фильтр по имени")
                                                  @RequestParam(required = false) String name,
                                                  @Parameter(description = "Номер страницы")
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @Parameter(description = "Размер страницы")
                                                  @RequestParam(defaultValue = "10") int size) {
        return service.getGroups(name, page, size);
    }

}