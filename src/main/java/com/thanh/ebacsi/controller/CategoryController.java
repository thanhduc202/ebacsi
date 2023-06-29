package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.dto.request.CategoryRequest;
import com.thanh.ebacsi.dto.response.CategoryResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.CategoryRepository;
import com.thanh.ebacsi.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/view")
    ResponseEntity<ResponseObject> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Category query success", categoryService.findAll()));
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<CategoryResponse> insertCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.findByCname(categoryRequest.getCname());
        if (category != null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new CategoryResponse());
        }
        Category category1 = new Category(categoryRequest);
        Category result = categoryService.save(category1);
        return ResponseEntity.status(HttpStatus.OK).body(new CategoryResponse(categoryService.save(result)));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.findById(categoryRequest.getCategoryId());
        if(category == null){
            throw new NotFoundException("Not found category");
        }
        category.setCname(categoryRequest.getCname());
        Category result = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(new CategoryResponse(result));
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
        boolean existed = categoryRepository.existsById(categoryId);
        if(existed){
            categoryRepository.deleteById(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Failed", "Not found category", ""));
    }
}
