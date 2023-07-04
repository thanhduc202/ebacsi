package com.thanh.ebacsi.service.category;

import com.thanh.ebacsi.dto.request.CategoryRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.dto.response.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    public Category save(Category category);

    public List<CategoryResponse> findAll();

    public Category findByCname(String name);

    public Category findById(Long id);

    ResponseEntity<CategoryResponse> insertCategory(CategoryRequest categoryRequest);

    ResponseEntity<CategoryResponse> updateCategory(CategoryRequest categoryRequest);

    ResponseEntity<ResponseObject> deleteCategory(Long categoryId);
}
