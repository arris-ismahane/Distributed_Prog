package com.progdist.emperia.controllers;

import com.progdist.emperia.model.Jewlery;
import com.progdist.emperia.model.inputs.JewleryInput;

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
@RequestMapping("/jewleries")
@RequiredArgsConstructor
public class EmperiaJewelryController {

    @Value("${handmadeglow.service.url}")
    private String databaseServiceURL;
    
    private final RestTemplate restTemplate;

    private static final String JEWELRY_API_PATH = "/api/jewleries";
    
    public EmperiaJewelryController() {
        this.restTemplate = new RestTemplate();
    }
    
    @GetMapping
    public List<Jewlery> getAllJewelry() {
        String url = databaseServiceURL + JEWELRY_API_PATH;
        Jewlery[] response = restTemplate.getForObject(url, Jewlery[].class);
        return Arrays.asList(response);
    }
    
    @GetMapping("/{id}")
    public Jewlery getJewelryById(@PathVariable long id) {
        String url = databaseServiceURL + JEWELRY_API_PATH + "/" + id;
        return restTemplate.getForObject(url, Jewlery.class);
    }
    
    @DeleteMapping("/{id}")
    public void deleteJewelry(@PathVariable long id) {
        String url = databaseServiceURL + JEWELRY_API_PATH + "/" + id;
        restTemplate.delete(url);
    }
    
    @PostMapping("/create")
    public Jewlery createJewelry(@RequestBody JewleryInput input) {
        String url = databaseServiceURL + JEWELRY_API_PATH + "/create";
        return restTemplate.postForObject(url, input, Jewlery.class);
    }
    
    @PutMapping("/update/{id}")
    public Jewlery updateJewelry(@PathVariable long id, @RequestBody JewleryInput input) {
        String url = databaseServiceURL + JEWELRY_API_PATH + "/update/" + id;
        // Using exchange instead of put to get the response body
        return restTemplate.exchange(url, org.springframework.http.HttpMethod.PUT, 
            new org.springframework.http.HttpEntity<>(input), Jewlery.class).getBody();
    }
}