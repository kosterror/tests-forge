package ru.kosterror.testsforge.filestorageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kosterror.testsforge.commonmodel.filestorageservice.FileMetaInfoDto;
import ru.kosterror.testsforge.filestorageservice.configuration.OpenApiConfiguration;
import ru.kosterror.testsforge.filestorageservice.service.FileStorageService;
import ru.kosterror.testsforge.securitystarter.model.JwtUser;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER_OR_STUDENT;
import static ru.kosterror.testsforge.securitystarter.util.RoleExpressions.TEACHER_OR_STUDENT_OR_SERVICE;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Файловое хранилище")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @PreAuthorize(TEACHER_OR_STUDENT)
    @Operation(summary = "Загрузить файл в хранилище", security = @SecurityRequirement(name = OpenApiConfiguration.JWT))
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FileMetaInfoDto uploadFile(@AuthenticationPrincipal JwtUser principal,
                                      @RequestParam("file") MultipartFile file
    ) {
        return fileStorageService.uploadFile(principal.userId(), file);
    }

    @PreAuthorize(TEACHER_OR_STUDENT)
    @Operation(summary = "Скачать файл", security = @SecurityRequirement(name = OpenApiConfiguration.JWT))
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) {
        var nameAndFile = fileStorageService.downloadFile(id);

        var contentDisposition = ContentDisposition.builder("file")
                .filename(nameAndFile.getLeft(), StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(nameAndFile.getRight()));
    }

    @PreAuthorize(TEACHER_OR_STUDENT_OR_SERVICE)
    @Operation(
            summary = "Получить метаинформацию о файле",
            security = {
                    @SecurityRequirement(name = OpenApiConfiguration.JWT),
                    @SecurityRequirement(name = OpenApiConfiguration.API_KEY)
            }
    )
    @GetMapping("/{id}/meta-info")
    public FileMetaInfoDto getFileMetaInfo(@PathVariable UUID id) {
        return fileStorageService.getFileMetaInfo(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_SERVICE')")
    @Operation(
            summary = "Проверить существование файлов. В ответе список ID несуществующих файлов",
            security = {
                    @SecurityRequirement(name = OpenApiConfiguration.API_KEY)
            }
    )
    @PostMapping("/check-existing")
    public List<UUID> checkExistingFiles(@RequestBody List<UUID> fileIds) {
        return fileStorageService.checkExistingFiles(fileIds);
    }

}
