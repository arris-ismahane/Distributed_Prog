package com.progdist.jewlery.controllers;

import com.progdist.jewlery.model.EmperiaUser;
import com.progdist.jewlery.model.inputs.EmperiaUserInput;
import com.progdist.jewlery.services.EmperiaUserService;

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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class EmperiaUserController {
    private final EmperiaUserService service;

    @GetMapping
    public List<EmperiaUser> getEmperiaUsers() {
        return service.getEmperiaUsers();
    }

    @GetMapping("{id}")
    public EmperiaUser getEmperiaUserById(@PathVariable long id) {
        return service.getEmperiaUserById(id);
    }

    @GetMapping("/username/{username}")
    public EmperiaUser getEmperiaUserByUsername(@PathVariable String username) {
        return service.getEmperiaUserByUsername(username);
    }
    

    @DeleteMapping("{id}")
    public void deleteEmperiaUser(@PathVariable long id) {
        service.deleteEmperiaUser(id);
    }

    @PostMapping("create")
    public EmperiaUser createEmperiaUser(@RequestBody EmperiaUserInput input) {
        return service.createEmperiaUser(input);
    }    
    
    @PutMapping("update/{id}")
    public EmperiaUser updateEmperiaUser(@PathVariable long id, @RequestBody EmperiaUserInput input) {
        return service.updateEmperiaUser(id, input);
    }
}
