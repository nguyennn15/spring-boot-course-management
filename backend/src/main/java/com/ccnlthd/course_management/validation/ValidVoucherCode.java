package com.ccnlthd.course_management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = VoucherCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
    public @interface ValidVoucherCode {

    String message() default
            "Mã voucher không hợp lệ. Phải có định dạng COURSE_xxxx (với xxxx là 4 chữ số)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}