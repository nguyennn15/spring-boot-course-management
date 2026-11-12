package com.ccnlthd.course_management.service.impl;

import com.ccnlthd.course_management.dto.response.CourseFeeResponse;
import com.ccnlthd.course_management.entity.Course;
import com.ccnlthd.course_management.exception.AppException;
import com.ccnlthd.course_management.exception.ErrorCode;
import com.ccnlthd.course_management.repository.CourseRepository;
import com.ccnlthd.course_management.service.PaymentPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CourseFeeService {

    private final CourseRepository courseRepository;
    private final PaymentPolicy defaultPolicy;
    private final PaymentPolicy discountPolicy;

    public CourseFeeService(CourseRepository courseRepository, PaymentPolicy defaultPolicy, @Qualifier("discountPaymentPolicy") PaymentPolicy discountPolicy) {
        this.courseRepository = courseRepository;
        this.defaultPolicy = defaultPolicy;
        this.discountPolicy = discountPolicy;
    }

    public CourseFeeResponse calculateFee(Long id, String policy) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        boolean useDiscount = "discount".equalsIgnoreCase(policy);

        PaymentPolicy selected = useDiscount ? discountPolicy : defaultPolicy;

        return new CourseFeeResponse(
                course.getId(),
                course.getPrice(),
                selected.getClass().getSimpleName(),
                selected.calculate(course.getPrice())
        );
    }
}