package com.example.api.control;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Custom validator to check Divisor
 */
@Component
public class DividerValidator implements ConstraintValidator<Divisor, Double> {

    @Override
    public void initialize(Divisor divisor) {
    }

    /**
     * Method used to validate Divisor
     * @param aDouble
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (isZero(aDouble)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("You can't divide by zero").addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isZero(Double aDouble) {
        return aDouble != null && !Pattern.matches("\\d+\\.\\d*[1-9]+\\d*|\\d*[1-9]+\\d*\\.\\d+", String.valueOf(aDouble));
    }
}
