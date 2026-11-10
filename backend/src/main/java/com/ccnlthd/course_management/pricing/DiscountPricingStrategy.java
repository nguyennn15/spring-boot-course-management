package com.ccnlthd.course_management.pricing;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("discountPricingStrategy")
public class DiscountPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice) {
        return basePrice.multiply(new BigDecimal("0.80"));
    }
}