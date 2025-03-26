package com.progdist.jewlery.controllers;

import com.progdist.jewlery.model.Jewlery;
import com.progdist.jewlery.services.JewleryService;

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
@RequestMapping("/api/jewleries")
@RequiredArgsConstructor
public class JewleryController {
    private final JewleryService service;

    @GetMapping
    public List<Jewlery> getJewleries() {
        return service.getJewleries();
    }

    @GetMapping("{id}")
    public Jewlery getJewleryById(@PathVariable long id) {
        return service.getJewleryById(id);
    }

    @DeleteMapping("{id}")
    public void deleteJewlery(long id) {
        service.deleteJewlery(id);
    }

    @PostMapping("create")
    public Jewlery createJewlery(@RequestBody Jewlery input) {
        return service.createJewlery(input);
    }    
    
    @PutMapping("update")
    public Jewlery updateJewlery(@RequestBody Jewlery input) {
        return service.updateJewlery(input);
    }
}
