package ru.kosterror.forms.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class ErrorResponse {

    @Schema(description = "Timestamp of the error", requiredMode = REQUIRED)
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code of the error", requiredMode = REQUIRED)
    private HttpStatusCode code;

    @Schema(description = "Error message", requiredMode = REQUIRED)
    private String error;

    @Schema(description = "Path of the request that caused the error", requiredMode = REQUIRED)
    private String path;

    @Schema(description = "Validation messages", requiredMode = NOT_REQUIRED)
    private Map<String, List<String>> validationMessages;

    public ErrorResponse(HttpStatusCode code,
                         String error,
                         String path,
                         Map<String, List<String>> validationMessages) {
        this.code = code;
        this.error = error;
        this.path = path;
        this.validationMessages = validationMessages;
    }

    public ErrorResponse(HttpStatusCode code,
                         String error,
                         String path) {
        this.code = code;
        this.error = error;
        this.path = path;
    }
}
