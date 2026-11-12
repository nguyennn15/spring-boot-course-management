package com.ccnlthd.course_management.service.impl;

import com.ccnlthd.course_management.service.PaymentPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("discountPaymentPolicy")
public class DiscountPaymentPolicy implements PaymentPolicy {

    @Override
    public BigDecimal calculate(BigDecimal originalPrice) {
        return originalPrice.multiply(new BigDecimal("0.80"));
    }
}