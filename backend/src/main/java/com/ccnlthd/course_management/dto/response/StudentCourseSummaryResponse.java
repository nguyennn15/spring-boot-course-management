package com.ccnlthd.course_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseSummaryResponse {

    private Long studentId;

    private String fullName;

    private String email;

    private long totalEnrolledCourses;
}