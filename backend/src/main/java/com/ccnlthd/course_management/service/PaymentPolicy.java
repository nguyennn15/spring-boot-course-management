package com.ccnlthd.course_management.service;

import java.math.BigDecimal;

public interface PaymentPolicy {
    BigDecimal calculate(BigDecimal originalPrice);
}
