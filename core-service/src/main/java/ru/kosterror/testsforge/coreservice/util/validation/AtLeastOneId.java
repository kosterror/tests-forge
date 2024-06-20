package ru.kosterror.testsforge.coreservice.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AtLeastOneIdValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneId {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
