package com.mandacarubroker.validations;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import lombok.Getter;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CustomValidator {

    private final Validator validator;

    public CustomValidator() {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator()) // Use ParameterMessageInterpolator to only interpolate custom messages
                .buildValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    public ExecutableValidator forExecutables() {
        return validator.forExecutables();
    }
}

