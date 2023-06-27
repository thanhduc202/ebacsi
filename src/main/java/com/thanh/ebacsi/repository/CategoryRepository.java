package com.thanh.ebacsi.repository;

import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.dto.response.CategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findByCname(String cname);

    @Query("select c from Category c ")
    public List<CategoryResponse> getAllCategory();

}
