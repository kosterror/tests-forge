package ru.kosterror.forms.filestorageservice.controller;

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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kosterror.forms.filestorageservice.dto.FileMetaInfoDto;
import ru.kosterror.forms.filestorageservice.service.FileStorageService;
import ru.kosterror.forms.securitystarterjwt.model.JwtUser;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static ru.kosterror.forms.filestorageservice.configuration.SpringDocConfiguration.JWT;

@RestController
@RequestMapping("/api/v1/file-storage")
@RequiredArgsConstructor
@Tag(name = "Файловое хранилище")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Operation(summary = "Загрузить файл в хранилище", security = @SecurityRequirement(name = JWT))
    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public FileMetaInfoDto uploadFile(@AuthenticationPrincipal JwtUser principal,
                                      @RequestParam("file") MultipartFile file
    ) {
        return fileStorageService.uploadFile(principal.userId(), file);
    }

    @Operation(summary = "Скачать файл")
    @GetMapping(value = "/download/{id}",
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

    @Operation(summary = "Получить метаинформацию о файле")
    @GetMapping("/{id}")
    public FileMetaInfoDto getFileMetaInfo(@PathVariable UUID id) {
        return fileStorageService.getFileMetaInfo(id);
    }

}
