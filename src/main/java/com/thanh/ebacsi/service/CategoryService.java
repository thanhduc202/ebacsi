package com.thanh.ebacsi.service;

import com.thanh.ebacsi.dto.request.CategoryRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.dto.response.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    List<CategoryResponse> findAll();

    Category findByCname(String name);

    Category findById(Long id);

    ResponseEntity<CategoryResponse> insertCategory(CategoryRequest categoryRequest);

    ResponseEntity<CategoryResponse> updateCategory(CategoryRequest categoryRequest);

    ResponseEntity<ResponseObject> deleteCategory(Long categoryId);
}
