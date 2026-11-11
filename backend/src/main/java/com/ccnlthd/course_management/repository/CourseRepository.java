package com.ccnlthd.course_management.repository;

import com.ccnlthd.course_management.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;

import java.math.BigDecimal;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Override
    @EntityGraph(attributePaths = "category")
    List<Course> findAll();

    boolean existsByCategory_Id(Long categoryId);

    List<Course> findByCategory_Id(Long categoryId);

    List<Course> findByStatusIgnoreCase(String status);

    List<Course> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("""
            select c
            from Course c
            where lower(c.category.name) = lower(:categoryName)
            """)
    List<Course> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("""
            select c
            from Course c
            where lower(c.title) like lower(concat('%', :keyword, '%'))
               or lower(c.description) like lower(concat('%', :keyword, '%'))
            """)
    List<Course> searchByKeyword(@Param("keyword") String keyword);

    @Query("""
        SELECT c
        FROM Course c
        WHERE (
            :keyword IS NULL
            OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        AND (
            :categoryId IS NULL
            OR c.category.id = :categoryId
        )
        """)
    Page<Course> searchCourses(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}
