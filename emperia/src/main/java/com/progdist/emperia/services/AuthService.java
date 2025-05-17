package com.progdist.emperia.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.progdist.emperia.model.inputs.EmperiaUserInput;

import com.progdist.emperia.model.EmperiaUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RestTemplate restTemplate;

    @Value("${handmadeglow.service.url}")
    private String userServiceUrl;

    private static final String USER_API_PATH = "/api/users";
    public EmperiaUser authenticate(String username, String password) {
        try {
            String url = userServiceUrl + USER_API_PATH + "/username/" + username;
            EmperiaUser user = restTemplate.getForObject(url, EmperiaUser.class);
            log.info("*****user : "+user);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            log.error("User : " + username + " is not found", e);
        }
        return null;
    }

    public EmperiaUser register( EmperiaUserInput input) {
        String url = userServiceUrl + USER_API_PATH + "/create";
        return restTemplate.postForObject(url, input, EmperiaUser.class);
    }
}
