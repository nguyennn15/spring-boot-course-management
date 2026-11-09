package com.ccnlthd.course_management.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StudentCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStudentCode {
    String message() default "Mã học viên không đúng định dạng SV-YYYY-XXXX (ví dụ SV-2024-0001)";
    Class[] groups() default {};
    Class[] payload() default {};
}
