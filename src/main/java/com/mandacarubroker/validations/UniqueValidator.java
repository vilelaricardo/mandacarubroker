package com.mandacarubroker.validations;
import com.mandacarubroker.domain.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<UniqueValidate, String> {
    private final UserRepository userRepository;

    public UniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && (userRepository.findByUsername(value).isEmpty());
    }
}
