package com.ccnlthd.course_management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CourseCodeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCourseCode {

    String message() default "Mã khóa học phải có dạng XX-YYYY " + "(2 chữ cái in hoa, dấu gạch ngang và 4 chữ số)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}