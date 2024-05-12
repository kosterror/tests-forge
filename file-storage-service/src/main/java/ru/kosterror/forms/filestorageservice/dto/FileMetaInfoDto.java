package ru.kosterror.forms.filestorageservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record FileMetaInfoDto(
        @Schema(description = "File identifier", requiredMode = REQUIRED)
        UUID id,

        @Schema(description = "Bucket name", requiredMode = REQUIRED)
        String bucket,

        @Schema(description = "File name", requiredMode = REQUIRED)
        String name,

        @Schema(description = "File size in MB", requiredMode = REQUIRED)
        double size,

        @Schema(description = "Identifier of the user who uploaded this file", requiredMode = REQUIRED)
        UUID ownerId,

        @Schema(description = "Date and time of file upload", requiredMode = REQUIRED)
        LocalDateTime uploadDateTime
) {
}
