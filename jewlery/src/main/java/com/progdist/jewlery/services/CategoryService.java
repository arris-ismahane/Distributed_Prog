package com.progdist.jewlery.services;

import com.progdist.jewlery.excpetions.NotFoundException;
import com.progdist.jewlery.model.Category;
import com.progdist.jewlery.model.inputs.CategoryInput;
import com.progdist.jewlery.repositories.CategoryRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public List<Category> getCategories() {
        return repository.findAll();
    }

    public Category getCategoryById(long id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException("Not Found Exception","Category not found"));
    }

    public void deleteCategory(long id) {
        repository.deleteById(id);
    }

    public Category createCategory(CategoryInput input) {
        return repository.save(input.toEntity());
    }    
    
    public Category updateCategory(long id, CategoryInput input) {
        var old_category = getCategoryById(id);
        var new_category = input.toEntity();
        new_category.setCreationDate(old_category.getCreationDate());
        new_category.setId(old_category.getId());
        return repository.save(new_category);
    }
}
