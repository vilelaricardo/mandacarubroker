package com.mandacarubroker.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface UniqueValidate {
    String message() default "This user already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
