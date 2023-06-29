package com.thanh.ebacsi.service.category;

import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public Category save(Category category);

    public List<CategoryResponse> findAll();

    public Category findByCname(String name);

    public Category findById(Long id);
}
