package com.ccnlthd.course_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailResponse {

    private Long id;

    private String name;

    private String description;

    private long totalCourses;
}