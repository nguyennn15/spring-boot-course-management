package com.ccnlthd.course_management.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemInitializationProbe {

    public SystemInitializationProbe() {
        log.info("[CONSTRUCTOR] SystemInitializationProbe được khởi tạo bằng new.");
    }

    @PostConstruct
    public void init() {
        log.info("[SYSTEM INIT] Khởi tạo các tham số hệ thống thành công sau khi DI hoàn tất.");
    }

    @PreDestroy
    public void cleanup() {
        log.info("[SYSTEM CLEANUP] Dọn dẹp tài nguyên trước khi Spring Context đóng.");
    }
}