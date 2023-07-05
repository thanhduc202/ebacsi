package com.thanh.ebacsi.service.impl;

import com.thanh.ebacsi.dto.request.CategoryRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.CategoryRepository;
import com.thanh.ebacsi.dto.response.CategoryResponse;
import com.thanh.ebacsi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> findAll() {
        List<CategoryResponse> category =  categoryRepository.getAllCategory();
        if(category.size()<0){
            throw new NotFoundException("Don't have any category existed") ;
        }
        return category;
    }

    @Override
    public Category findByCname(String name) {
        return categoryRepository.findByCname(name);
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("not found category");
        }
        return category.get();
    }

    @Override
    public ResponseEntity<CategoryResponse> insertCategory(CategoryRequest categoryRequest) {
        Category categoryExist = findByCname(categoryRequest.getCname());
        if (categoryExist != null) {
            throw new NotFoundException("Category have exist");
        }
        Category category = new Category(categoryRequest);
        Category result = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(new CategoryResponse(result));
    }

    @Override
    public ResponseEntity<CategoryResponse> updateCategory(CategoryRequest categoryRequest) {
        Category category = findById(categoryRequest.getCategoryId());
        if(category == null){
            throw new NotFoundException("Not found category");
        }
        Category existCategory = categoryRepository.findByCname(categoryRequest.getCname());
        if(existCategory!=null && !existCategory.getCategoryId().equals(category.getCategoryId())){
            throw new NotFoundException("category have exist");
        }
        category.setCname(categoryRequest.getCname());
        Category result = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(new CategoryResponse(result));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCategory(Long categoryId) {
        boolean existed = categoryRepository.existsById(categoryId);
        if(existed){
            categoryRepository.deleteById(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Failed", "Not found category", ""));
    }
}
