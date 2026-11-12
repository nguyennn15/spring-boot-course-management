package com.ccnlthd.course_management.service.impl;

import com.ccnlthd.course_management.component.RegistrationSession;
import com.ccnlthd.course_management.dto.response.RegistrationSessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationSessionService {

    private final ObjectProvider<RegistrationSession> sessionProvider;

    public RegistrationSessionResponse testBeanScope() {
        RegistrationSession first = sessionProvider.getObject();
        RegistrationSession second = sessionProvider.getObject();

        return new RegistrationSessionResponse(
                first.getSessionId(),
                second.getSessionId(),
                first != second
        );
    }
}