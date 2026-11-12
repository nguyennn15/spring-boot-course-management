package com.ccnlthd.course_management.dto.response;

public record ValidationError(
        String field,
        String message
) {
}