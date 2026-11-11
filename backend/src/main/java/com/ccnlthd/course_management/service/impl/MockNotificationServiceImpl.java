package com.ccnlthd.course_management.service.impl;

import com.ccnlthd.course_management.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(
        name = "app.notification.enabled",
        havingValue = "false",
        matchIfMissing = true
)
public class MockNotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(String email, String message) {
        log.info("[MOCK NOTIFICATION] Giả lập thông báo tới {}: {}", email, message);
    }
}