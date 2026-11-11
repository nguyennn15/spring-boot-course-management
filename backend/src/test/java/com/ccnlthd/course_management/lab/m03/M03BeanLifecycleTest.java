package com.ccnlthd.course_management.lab.m03;

import com.ccnlthd.course_management.config.SystemInitializationProbe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class M03BeanLifecycleTest {

    @Test
    void shouldRunLifecycleCallbacksInOrder(CapturedOutput output) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(SystemInitializationProbe.class);

        try {
            SystemInitializationProbe first = context.getBean(SystemInitializationProbe.class);

            SystemInitializationProbe second = context.getBean(SystemInitializationProbe.class);

            // Singleton: lấy Bean hai lần vẫn là cùng một đối tượng.
            assertSame(first, second);

            String beforeClose = output.getAll();

            // Constructor và PostConstruct đã tự chạy.
            assertTrue(beforeClose.contains("[CONSTRUCTOR]"));
            assertTrue(beforeClose.contains("[SYSTEM INIT]"));

            // Context chưa đóng nên PreDestroy chưa chạy.
            assertFalse(beforeClose.contains("[SYSTEM CLEANUP]"));

            // Constructor phải chạy trước PostConstruct.
            assertTrue(beforeClose.indexOf("[CONSTRUCTOR]") < beforeClose.indexOf("[SYSTEM INIT]"));
        } finally {
            context.close();
        }

        String afterClose = output.getAll();

        // Đóng context làm Spring tự gọi PreDestroy.
        assertTrue(afterClose.contains("[SYSTEM CLEANUP]"));

        // Cleanup phải xuất hiện sau Init.
        assertTrue(afterClose.indexOf("[SYSTEM INIT]") < afterClose.indexOf("[SYSTEM CLEANUP]"));
    }
}