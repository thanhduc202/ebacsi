package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.CategoryRequest;
import com.thanh.ebacsi.dto.response.CategoryResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/view")
    ResponseEntity<ResponseObject> getAllCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Category query success", categoryService.findAll()));
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<CategoryResponse> insertCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.insertCategory(categoryRequest);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(categoryRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
       return categoryService.deleteCategory(categoryId);
    }
}
