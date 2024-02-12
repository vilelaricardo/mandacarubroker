package com.mandacarubroker.validation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public final class RecordValidation {
    private RecordValidation() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateRequestDTO(final Object requestDTO) throws ConstraintViolationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(requestDTO);

        if (violations.isEmpty()) {
            return;
        }

        StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

        for (ConstraintViolation<Object> violation : violations) {
            errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
        }

        throw new ConstraintViolationException(errorMessage.toString(), violations);
    }
}
