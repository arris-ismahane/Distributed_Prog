package com.progdist.jewlery.controllers;

import com.progdist.jewlery.model.Jewlery;
import com.progdist.jewlery.model.JewleryDTO;
import com.progdist.jewlery.model.inputs.JewleryInput;
import com.progdist.jewlery.services.JewleryService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jewleries")
@RequiredArgsConstructor
public class JewleryController {
    private final JewleryService service;

    @GetMapping
    public List<JewleryDTO> getJewleries() {
        return service.getJewleries();
    }

    @GetMapping("{id}")
    public Jewlery getJewleryById(@PathVariable long id) {
        return service.getJewleryById(id);
    }

    @DeleteMapping("{id}")
    public void deleteJewlery(@PathVariable long id) {
        service.deleteJewlery(id);
    }

    @PostMapping("create")
    public Jewlery createJewlery(@RequestBody JewleryInput input, HttpServletRequest request) {
        return service.createJewlery(input, request);
    }

    @PutMapping("update/{id}")
    public Jewlery updateJewlery(@PathVariable long id, @RequestBody JewleryInput input) {
        return service.updateJewlery(id, input);
    }

    @PutMapping("{jewelryId}/images")
    public Jewlery uploadImages(
            @PathVariable Long jewelryId,
            @RequestParam("images") List<MultipartFile> imageFiles) throws IOException {

        // Convert MultipartFile list to byte arrays
        List<byte[]> imageByteArrays = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            imageByteArrays.add(file.getBytes());
        }

        // Call service method to update images
        return service.updateJewleryImages(jewelryId, imageByteArrays);
    }

    @GetMapping("/user/{userId}")
    public List<JewleryDTO> getJewleriesByUserOwnership(
            @PathVariable long userId,
            @RequestParam boolean owned,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return service.getJewleriesByUserOwnership(userId, owned, categoryId, sortBy, order);
    }

}
