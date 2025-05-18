package com.progdist.emperia.controllers;

import com.progdist.emperia.model.EmperiaUser;
import com.progdist.emperia.model.Jewlery;
import com.progdist.emperia.model.inputs.JewleryInput;
import com.progdist.emperia.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/jewelries")
@RequiredArgsConstructor
public class EmperiaJewelryController {

    @Value("${handmadeglow.service.url}")
    private String databaseServiceURL;

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    private static final String JEWELRY_API_PATH = "/api/jewleries";
    private static final String USER_API_PATH = "/api/users";

    @GetMapping
    public List<Jewlery> getJewelryForLoggedUser(
            @RequestParam(defaultValue = "false") boolean owned,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            HttpServletRequest request) {

        // Extract JWT token from header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);

        // Extract username from token using your JwtUtil (make sure you inject it)
        String username = jwtUtil.getUsernameFromToken(token);

        // Get user object or userId from username
        String userUrl = databaseServiceURL + USER_API_PATH + "/username/" + username;
        var user = restTemplate.getForObject(userUrl, EmperiaUser.class);

        Long userId = user.getId(); // adjust this to your actual user ID getter
        // prepare backend URL with userId
        String url = databaseServiceURL + JEWELRY_API_PATH + "/user/" + userId + "?owned=" + owned+"&sortBy="+ sortBy+"&order="+order;
        if (categoryId != null) {
            url = url+"&categoryId="+categoryId;
        }
        // forward Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // call backend with token and userId
        ResponseEntity<Jewlery[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Jewlery[].class);

        return Arrays.asList(response.getBody());
    }

    @GetMapping("/all")
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
    public Jewlery createJewelry(@RequestBody JewleryInput input, HttpServletRequest request) {
        String url = databaseServiceURL + JEWELRY_API_PATH + "/create";

        // Extract JWT token from request header
        String authHeader = request.getHeader("Authorization");

        // Prepare headers with the Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader); // Forward the token
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request entity with body and headers
        HttpEntity<JewleryInput> entity = new HttpEntity<>(input, headers);

        // Make the POST request
        return restTemplate.postForObject(url, entity, Jewlery.class);
    }

    @PutMapping("/update/{id}")
    public Jewlery updateJewelry(@PathVariable long id, @RequestBody JewleryInput input) {
        String url = databaseServiceURL + JEWELRY_API_PATH + "/update/" + id;
        // Using exchange instead of put to get the response body
        return restTemplate.exchange(url, org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(input), Jewlery.class).getBody();
    }

    @PutMapping("images/{jewelryId}")
    public Jewlery uploadImages(@PathVariable Long jewelryId, @RequestParam("images") List<MultipartFile> imageFiles)
            throws IOException {
        String url = databaseServiceURL + JEWELRY_API_PATH + "/" + jewelryId + "/images";

        org.springframework.util.MultiValueMap<String, Object> body = new org.springframework.util.LinkedMultiValueMap<>();

        for (MultipartFile file : imageFiles) {
            // Create a resource from the file's bytes
            org.springframework.core.io.ByteArrayResource resource = new org.springframework.core.io.ByteArrayResource(
                    file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            body.add("images", resource);
        }

        // Set headers for multipart form data
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);

        // Create the request entity
        org.springframework.http.HttpEntity<org.springframework.util.MultiValueMap<String, Object>> requestEntity = new org.springframework.http.HttpEntity<>(
                body, headers);

        // Make the PUT request and return the response
        return restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.PUT,
                requestEntity,
                Jewlery.class).getBody();
    }
}
