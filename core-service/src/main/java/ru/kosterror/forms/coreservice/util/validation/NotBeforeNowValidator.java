package ru.kosterror.forms.coreservice.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class NotBeforeNowValidator implements ConstraintValidator<NotBeforeNow, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        var now = LocalDateTime.now();
        return now.isBefore(value);
    }

}
