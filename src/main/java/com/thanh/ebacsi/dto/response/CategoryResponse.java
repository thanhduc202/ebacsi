package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CategoryResponse {
    private String status = "SUCCESS";
    private Long categoryId;
    private String cname;

    public CategoryResponse(Category category) {
        this.categoryId = category.getCategoryId();
        this.cname = category.getCname();
    }
}
