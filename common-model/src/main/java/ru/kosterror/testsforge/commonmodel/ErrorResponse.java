package ru.kosterror.testsforge.commonmodel;

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

    @Schema(description = "Дата и время, когда случилась ошибка", requiredMode = REQUIRED)
    private LocalDateTime timestamp;

    @Schema(description = "HTTP статус код", requiredMode = REQUIRED)
    private HttpStatusCode code;

    @Schema(description = "Сообщение об ошибке", requiredMode = REQUIRED)
    private String error;

    @Schema(description = "Путь запроса", requiredMode = REQUIRED)
    private String path;

    @Schema(description = "Ошибки валидации. Ключ - поле из тела запроса, значение - список ошибок валидации",
            requiredMode = NOT_REQUIRED
    )
    private Map<String, List<String>> validationMessages;

    public ErrorResponse(HttpStatusCode code,
                         String error,
                         String path,
                         Map<String, List<String>> validationMessages) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.error = error;
        this.path = path;
        this.validationMessages = validationMessages;
    }

    public ErrorResponse(HttpStatusCode code,
                         String error,
                         String path) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.error = error;
        this.path = path;
    }
}
