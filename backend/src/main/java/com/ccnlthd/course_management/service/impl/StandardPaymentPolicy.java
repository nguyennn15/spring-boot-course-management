package com.ccnlthd.course_management.service.impl;

import com.ccnlthd.course_management.service.PaymentPolicy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary
public class StandardPaymentPolicy implements PaymentPolicy {

    @Override
    public BigDecimal calculate(BigDecimal originalPrice) {
        return originalPrice;
    }
}