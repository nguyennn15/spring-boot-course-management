package com.ccnlthd.course_management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VoucherCodeValidator implements ConstraintValidator<ValidVoucherCode, String> {

    private static final String REGEX = "^COURSE_\\d{4}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(REGEX);
    }
}