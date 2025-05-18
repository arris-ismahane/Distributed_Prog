package com.progdist.jewlery.controllers;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class DummyController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world from HandmadeGlow JPA";
    }    
}
