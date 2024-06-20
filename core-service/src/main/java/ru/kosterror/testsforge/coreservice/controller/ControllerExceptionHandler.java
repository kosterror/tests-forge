package ru.kosterror.testsforge.coreservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kosterror.testsforge.commonmodel.ErrorResponse;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    private static ErrorResponse buildErrorResponse(String error, String uri, HttpStatus status) {
        return new ErrorResponse(status, error, uri);
    }

    private static void logException(HttpServletRequest request, Exception exception) {
        log.error("Exception occurred during processing request: {} {}",
                request.getMethod(),
                request.getRequestURI(), exception
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(HttpServletRequest request,
                                                                   MethodArgumentNotValidException exception
    ) {
        logException(request, exception);

        var errors = new HashMap<String, List<String>>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach(rawError -> {
                    if (rawError instanceof FieldError error) {
                        String fieldName = error.getObjectName() + "." + error.getField();
                        String errorMessage = error.getDefaultMessage();

                        addErrorMessage(errors, fieldName, errorMessage);
                    } else if (rawError instanceof ObjectError error) {
                        String fieldName = error.getObjectName();
                        String errorMessage = error.getDefaultMessage();

                        addErrorMessage(errors, fieldName, errorMessage);
                    }
                });

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(response.getCode()).body(response);
    }

    private static void addErrorMessage(HashMap<String, List<String>> errors,
                                        String fieldName,
                                        String errorMessage
    ) {
        if (errors.containsKey(fieldName)) {
            var messages = errors.get(fieldName);
            messages.add(errorMessage);
        } else {
            var messages = new ArrayList<String>();
            messages.add(errorMessage);
            errors.put(fieldName, messages);
        }
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest request,
                                                                   BadRequestException exception) {
        logException(request, exception);

        ErrorResponse response = buildErrorResponse(exception.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );

        return ResponseEntity.status(response.getCode()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request,
                                                                 NotFoundException exception) {
        logException(request, exception);

        ErrorResponse response = buildErrorResponse(exception.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND
        );

        return ResponseEntity.status(response.getCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request,
                                                         Exception exception) {
        logException(request, exception);

        ErrorResponse response = buildErrorResponse("Internal service error",
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return ResponseEntity.status(response.getCode()).body(response);
    }

}