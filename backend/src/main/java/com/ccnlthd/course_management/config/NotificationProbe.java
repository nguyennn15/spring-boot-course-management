package com.ccnlthd.course_management.config;

import com.ccnlthd.course_management.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationProbe implements CommandLineRunner {

    private final NotificationService notificationService;

    @Override
    public void run(String... args) {
        log.info("[NOTIFICATION PROBE] Bean được chọn: {}", notificationService.getClass().getSimpleName());

        notificationService.sendNotification("student@example.com", "Đăng ký khóa học thành công");
    }
}