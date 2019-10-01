package com.example.api.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DividerValidatorTest {

    private DividerValidator dividerValidator;

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @Mock
    ConstraintValidatorContext.ConstraintViolationBuilder constraintBuilder;

    @Test
    public void validateCorrectValue() {
        dividerValidator = new DividerValidator();
        assertTrue(dividerValidator.isValid(0012.20, constraintValidatorContext));
    }

    @Test
    public void validateForbiddenValue() {
        dividerValidator = new DividerValidator();
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(constraintBuilder);
        assertFalse(dividerValidator.isValid(0.0, constraintValidatorContext));
    }

    @Test
    public void validateForbiddenValueInDifferentFormat() {
        dividerValidator = new DividerValidator();
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(constraintBuilder);
        assertFalse(dividerValidator.isValid(0000.0, constraintValidatorContext));
    }
}
