package com.progdist.emperia.controllers;

import com.progdist.emperia.model.Category;
import com.progdist.emperia.model.inputs.CategoryInput;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class EmperiaCategoryController {

    @Value("${handmadeglow.service.url}")
    private String databaseServiceURL;
    
    private final RestTemplate restTemplate;
    private static final String CATEGORY_API_PATH = "/api/categories";
    
    public EmperiaCategoryController() {
        this.restTemplate = new RestTemplate();
    }
    
    @GetMapping
    public List<Category> getAllCategories() {
        String url = databaseServiceURL + CATEGORY_API_PATH;
        Category[] response = restTemplate.getForObject(url, Category[].class);
        return Arrays.asList(response);
    }
    
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable long id) {
        String url = databaseServiceURL + CATEGORY_API_PATH + "/" + id;
        return restTemplate.getForObject(url, Category.class);
    }
    
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        String url = databaseServiceURL + CATEGORY_API_PATH + "/" + id;
        restTemplate.delete(url);
    }
    
    @PostMapping("/create")
    public Category createCategory(@RequestBody CategoryInput input) {
        String url = databaseServiceURL + CATEGORY_API_PATH + "/create";
        return restTemplate.postForObject(url, input, Category.class);
    }
    
    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable long id, @RequestBody CategoryInput input) {
        String url = databaseServiceURL + CATEGORY_API_PATH + "/update/" + id;
        // Using exchange instead of put to get the response body
        return restTemplate.exchange(url, org.springframework.http.HttpMethod.PUT, 
            new org.springframework.http.HttpEntity<>(input), Category.class).getBody();
    }
}