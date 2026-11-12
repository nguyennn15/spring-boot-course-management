package com.ccnlthd.course_management;

import com.ccnlthd.course_management.service.PaymentPolicy;
import com.ccnlthd.course_management.service.impl.DiscountPaymentPolicy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseManagementApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(CourseManagementApplication.class, args);

		PaymentPolicy manualPolicy = new DiscountPaymentPolicy();

		boolean managed = context.getBeansOfType(PaymentPolicy.class).containsValue(manualPolicy);

		System.out.println("[IOC DEMO] Đối tượng tạo bằng new là Bean: " + managed);
	}

}
