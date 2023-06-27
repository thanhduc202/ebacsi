package com.thanh.ebacsi.service.category;

import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.CategoryRepository;
import com.thanh.ebacsi.dto.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        List<CategoryResponse> category =  categoryRepository.getAllCategory();
        if(category.size()<0){
            throw new NotFoundException("Don't have any category existed") ;
        }
        return category;
    }

    @Override
    public List<Category> findByCname(String name) {
        return categoryRepository.findByCname(name);
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            System.out.println("H");
        }
        return category.get();
    }
}
