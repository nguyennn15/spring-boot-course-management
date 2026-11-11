package com.ccnlthd.course_management.service;

import com.ccnlthd.course_management.dto.request.CourseRequest;
import com.ccnlthd.course_management.dto.response.CourseResponse;
import com.ccnlthd.course_management.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseRequest request);

    CourseResponse getCourseById(Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse updateCourse(Long id, CourseRequest request);

    void deleteCourse(Long id);

    PageResponse<CourseResponse> searchCourses(String keyword, Long categoryId, Pageable pageable);
}
