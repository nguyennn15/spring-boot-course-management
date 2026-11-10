package com.ccnlthd.course_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoursePricePreviewResponse {

    private Long courseId;

    private BigDecimal originalPrice;

    private BigDecimal defaultPrice;

    private BigDecimal discountedPrice;
}