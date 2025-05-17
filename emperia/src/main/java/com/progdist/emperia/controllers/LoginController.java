package com.progdist.emperia.controllers;

import com.progdist.emperia.model.EmperiaUser;
import com.progdist.emperia.model.LoginRequest;
import com.progdist.emperia.security.JwtUtil;
import com.progdist.emperia.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.progdist.emperia.model.inputs.EmperiaUserInput;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        EmperiaUser user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getEmperiaUserType().name());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getEmperiaUserType().name());

        // ✅ Add additional user fields here:
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("birthdate", user.getBirthDate()); // Make sure it's serialized properly

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody EmperiaUserInput input) {
        try {
            EmperiaUser createdUser = authService.register(input);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
