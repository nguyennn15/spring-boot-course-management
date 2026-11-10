package com.ccnlthd.course_management.service;

import com.ccnlthd.course_management.dto.request.CategoryCreateRequest;
import com.ccnlthd.course_management.dto.request.CategoryRequest;
import com.ccnlthd.course_management.dto.response.CategoryDetailResponse;
import com.ccnlthd.course_management.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryDetailResponse createCategory(CategoryCreateRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

}
