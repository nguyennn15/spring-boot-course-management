package com.ccnlthd.course_management.dto.response;

public record CourseItemResponse(
        Long id,
        String title,
        String categoryName
) {
}