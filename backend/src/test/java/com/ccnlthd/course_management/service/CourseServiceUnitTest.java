package com.ccnlthd.course_management.service;

import com.ccnlthd.course_management.dto.request.CourseRequest;
import com.ccnlthd.course_management.dto.response.CourseResponse;
import com.ccnlthd.course_management.entity.Category;
import com.ccnlthd.course_management.entity.Course;
import com.ccnlthd.course_management.exception.AppException;
import com.ccnlthd.course_management.exception.ErrorCode;
import com.ccnlthd.course_management.repository.CategoryRepository;
import com.ccnlthd.course_management.repository.CourseRepository;
import com.ccnlthd.course_management.repository.EnrollmentRepository;
import com.ccnlthd.course_management.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceUnitTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private CourseDetailCacheInvalidator cacheInvalidator;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void createCourse_success() {
        CourseRequest request = validRequest(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Backend");

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(courseRepository.save(any(Course.class)))
                .thenAnswer(invocation -> {
                    Course saved = invocation.getArgument(0);
                    saved.setId(100L);
                    return saved;
                });

        CourseResponse response = courseService.createCourse(request);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(1L, response.getCategoryId());
        assertEquals("Backend", response.getCategoryName());
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(request.getPrice(), response.getPrice());
        assertEquals(request.getLevel(), response.getLevel());
        assertEquals(request.getStatus(), response.getStatus());

        verify(categoryRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void createCourse_categoryNotFound() {
        CourseRequest request = validRequest(999L);

        when(categoryRepository.findById(999L))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(
                AppException.class,
                () -> courseService.createCourse(request)
        );

        assertEquals(
                ErrorCode.CATEGORY_NOT_FOUND,
                exception.getErrorCode()
        );

        verify(categoryRepository, times(1)).findById(999L);
        verify(courseRepository, never()).save(any(Course.class));
    }

    private CourseRequest validRequest(Long categoryId) {
        CourseRequest request = new CourseRequest();
        request.setCategoryId(categoryId);
        request.setTitle("Java Unit Test");
        request.setDescription("Kiểm thử tầng Service");
        request.setPrice(new BigDecimal("300000"));
        request.setLevel("BEGINNER");
        request.setStatus("DRAFT");
        return request;
    }
}