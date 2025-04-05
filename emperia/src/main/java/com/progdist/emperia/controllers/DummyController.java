package com.progdist.emperia.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/")
public class DummyController {

    @Value("${handmadeglow.service.url}")
    String databaseServiceURL;    


    @GetMapping("/")
    public String helloWorld() {
        RestTemplate restTemplate = new RestTemplate();
            String s = restTemplate.getForObject(databaseServiceURL, String.class);
        return "Hello world from Epmeria API \n" + s;
    }
    
}
