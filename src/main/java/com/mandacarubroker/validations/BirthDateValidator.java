package com.mandacarubroker.validations;
import com.mandacarubroker.domain.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<BirthDateValidate, LocalDate> {

    public BirthDateValidator() {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        final int MINIMUM_AGE = 18;
        LocalDate todayDate = LocalDate.now();
        return value.isBefore(todayDate)
                && value.until(todayDate).getYears()>=MINIMUM_AGE;
    }
}
