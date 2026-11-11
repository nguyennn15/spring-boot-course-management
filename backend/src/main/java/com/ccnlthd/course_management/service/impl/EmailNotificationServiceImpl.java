package com.ccnlthd.course_management.service.impl;

import com.ccnlthd.course_management.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(
        name = "app.notification.enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class EmailNotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(String email, String message) {
        log.info("[EMAIL NOTIFICATION] Gửi email thật tới {}: {}", email, message);
    }
}