package com.progdist.jewlery.services;

import com.progdist.jewlery.model.Category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public List<Category> getCategories() {
        return List.of();
    }

    public Category getCategoryById(long id) {
        return new Category();
    }

    public void deleteCategory(long id) {
        
    }

    public Category createCategory(Category input) {
        return new Category();
    }    
    
    public Category updateCategory(Category input) {
        return new Category();
    }
}
