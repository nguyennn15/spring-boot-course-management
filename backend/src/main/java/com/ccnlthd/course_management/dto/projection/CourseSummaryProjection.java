package com.ccnlthd.course_management.dto.projection;

import java.math.BigDecimal;

public interface CourseSummaryProjection {

    Long getId();

    String getTitle();

    BigDecimal getPrice();

    String getCategoryName();
}