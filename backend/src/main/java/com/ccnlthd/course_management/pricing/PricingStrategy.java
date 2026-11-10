package com.ccnlthd.course_management.pricing;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(BigDecimal basePrice);
}