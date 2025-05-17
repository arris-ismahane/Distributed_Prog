package com.progdist.emperia.controllers;

import com.progdist.emperia.model.EmperiaUser;
import com.progdist.emperia.model.inputs.EmperiaUserInput;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class EmperiaUserController {

    @Value("${handmadeglow.service.url}")
    private String databaseServiceURL;

    private final RestTemplate restTemplate;

    private static final String USER_API_PATH = "/api/users";

    @GetMapping
    public List<EmperiaUser> getAllUsers() {
        String url = databaseServiceURL + USER_API_PATH;
        EmperiaUser[] response = restTemplate.getForObject(url, EmperiaUser[].class);
        return Arrays.asList(response);
    }

    @GetMapping("/{id}")
    public EmperiaUser getUserById(@PathVariable long id) {
        String url = databaseServiceURL + USER_API_PATH + "/" + id;
        return restTemplate.getForObject(url, EmperiaUser.class);
    }
    @GetMapping("/username/{username}")
    public EmperiaUser getEmperiaUserByUsername(@PathVariable String username) {
        String url = databaseServiceURL + USER_API_PATH + "/username" + username;
        return restTemplate.getForObject(url, EmperiaUser.class);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        String url = databaseServiceURL + USER_API_PATH + "/" + id;
        restTemplate.delete(url);
    }

    @PostMapping("/create")
    public EmperiaUser createUser(@RequestBody EmperiaUserInput input) {
        String url = databaseServiceURL + USER_API_PATH + "/create";
        return restTemplate.postForObject(url, input, EmperiaUser.class);
    }

    @PutMapping("/update/{id}")
    public EmperiaUser updateUser(@PathVariable long id, @RequestBody EmperiaUserInput input) {
        String url = databaseServiceURL + USER_API_PATH + "/update/" + id;
        // Using exchange instead of put to get the response body
        return restTemplate.exchange(url, org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(input), EmperiaUser.class).getBody();
    }
}