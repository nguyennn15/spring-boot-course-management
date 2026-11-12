package com.ccnlthd.course_management.dto.response;

import java.math.BigDecimal;

public record CourseFeeResponse(
        Long courseId,
        BigDecimal originalPrice,
        String appliedPolicy,
        BigDecimal finalPrice
) {
}