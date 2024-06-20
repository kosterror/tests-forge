package ru.kosterror.testsforge.coreservice.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotBeforeNowValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBeforeNow {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
