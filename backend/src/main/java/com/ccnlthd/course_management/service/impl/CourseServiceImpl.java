package com.ccnlthd.course_management.service.impl;

import com.ccnlthd.course_management.dto.request.CourseRequest;
import com.ccnlthd.course_management.dto.response.CoursePricePreviewResponse;
import com.ccnlthd.course_management.dto.response.CourseResponse;
import com.ccnlthd.course_management.entity.Category;
import com.ccnlthd.course_management.entity.Course;
import com.ccnlthd.course_management.exception.AppException;
import com.ccnlthd.course_management.exception.ErrorCode;
import com.ccnlthd.course_management.pricing.PricingStrategy;
import com.ccnlthd.course_management.repository.CategoryRepository;
import com.ccnlthd.course_management.repository.CourseRepository;
import com.ccnlthd.course_management.repository.EnrollmentRepository;
import com.ccnlthd.course_management.service.CourseDetailCacheInvalidator;
import com.ccnlthd.course_management.service.CourseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseDetailCacheInvalidator cacheInvalidator;

    private final PricingStrategy defaultStrategy;
    private final PricingStrategy discountStrategy;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            CategoryRepository categoryRepository,
            EnrollmentRepository enrollmentRepository,
            CourseDetailCacheInvalidator cacheInvalidator,
            PricingStrategy defaultStrategy,
            @Qualifier("discountPricingStrategy")
            PricingStrategy discountStrategy
    ) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.cacheInvalidator = cacheInvalidator;
        this.defaultStrategy = defaultStrategy;
        this.discountStrategy = discountStrategy;
    }

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Course course = new Course();
        course.setCategory(category);
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setLevel(request.getLevel());
        course.setStatus(request.getStatus());

        Course savedCourse = courseRepository.save(course);

        return CourseResponse.from(savedCourse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "courseDetails", key = "#id")
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        return CourseResponse.from(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        course.setCategory(category);
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setLevel(request.getLevel());
        course.setStatus(request.getStatus());

        CourseResponse response = CourseResponse.from(courseRepository.save(course));
        cacheInvalidator.evictAfterCommit(id);
        return response;
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        if (enrollmentRepository.existsByCourse_Id(id)) {
            throw new AppException(ErrorCode.COURSE_HAS_ENROLLMENTS);
        }
        courseRepository.delete(course);
        cacheInvalidator.evictAfterCommit(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CoursePricePreviewResponse getPricePreview(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(
                        () -> new AppException(ErrorCode.COURSE_NOT_FOUND)
                );
        BigDecimal originalPrice = course.getPrice();

        BigDecimal defaultPrice = defaultStrategy.calculatePrice(originalPrice);

        BigDecimal discountedPrice = discountStrategy.calculatePrice(originalPrice);

        return new CoursePricePreviewResponse(
                course.getId(),
                originalPrice,
                defaultPrice,
                discountedPrice
        );
    }
}
