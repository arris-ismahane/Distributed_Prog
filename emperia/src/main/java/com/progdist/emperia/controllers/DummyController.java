package com.progdist.emperia.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DummyController {

    @Value("${handmadeglow.service.url}")
    String databaseServiceURL;    
    private final RestTemplate restTemplate;
    

    @GetMapping("/")
    public String helloWorld() {
        String s = restTemplate.getForObject(databaseServiceURL, String.class);
        return "Hello world from Epmeria API \n" + s;
    }
    
}
