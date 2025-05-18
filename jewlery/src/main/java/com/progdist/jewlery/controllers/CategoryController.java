package com.progdist.jewlery.controllers;

import com.progdist.jewlery.model.Category;
import com.progdist.jewlery.model.inputs.CategoryInput;
import com.progdist.jewlery.services.CategoryService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public List<Category> getCategories() {
        return service.getCategories();
    }

    @GetMapping("{id}")
    public Category getCategoryById(@PathVariable long id) {
        return service.getCategoryById(id);
    }

    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable long id) {
        service.deleteCategory(id);
    }

    @PostMapping("create")
    public Category createCategory(@RequestBody CategoryInput input) {
        return service.createCategory(input);
    }    
    
    @PutMapping("update/{id}")
    public Category updateCategory(@PathVariable long id,@RequestBody CategoryInput input) {
        return service.updateCategory(id, input);
    }
}
