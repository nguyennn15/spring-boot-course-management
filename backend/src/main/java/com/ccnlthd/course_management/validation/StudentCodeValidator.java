package com.ccnlthd.course_management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentCodeValidator implements ConstraintValidator<ValidStudentCode, String> {
    private static final String REGEX = "^SV-202[0-9]-\\d{4}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.trim().isEmpty()){
            return true;
        }
        return value.matches(REGEX);
    }
}
