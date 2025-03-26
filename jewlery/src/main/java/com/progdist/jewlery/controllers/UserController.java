package com.progdist.jewlery.controllers;

import com.progdist.jewlery.model.User;
import com.progdist.jewlery.services.UserService;

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
public class UserController {
    private final UserService service;

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable long id) {
        return service.getUserById(id);
    }

    @DeleteMapping("{id}")
    public void deleteUser(long id) {
        service.deleteUser(id);
    }

    @PostMapping("create")
    public User createUser(@RequestBody User input) {
        return service.createUser(input);
    }    
    
    @PutMapping("update")
    public User updateUser(@RequestBody User input) {
        return service.updateUser(input);
    }
}
