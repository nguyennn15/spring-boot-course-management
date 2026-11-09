package com.ccnlthd.course_management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class CoursePriceValidator implements ConstraintValidator<ValidCoursePrice, BigDecimal> {
    private static final BigDecimal MIN_PRICE = new BigDecimal("100000");

    private static final BigDecimal STEP = new BigDecimal("50000");

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        if(value.compareTo(MIN_PRICE)<=0){
            return false;
        }
        return value.remainder(STEP).compareTo(BigDecimal.ZERO) == 0;
    }
}