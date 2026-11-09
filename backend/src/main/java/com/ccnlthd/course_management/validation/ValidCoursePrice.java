package com.ccnlthd.course_management.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CoursePriceValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCoursePrice {
    String message() default "Giá khóa học phải từ 100.000 VNĐ và là bội số của 50.000 VNĐ";
    Class[] groups() default {};
    Class[] payload() default {};
}
