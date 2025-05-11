package com.progdist.jewlery.controllers;

import com.progdist.jewlery.model.Jewlery;
import com.progdist.jewlery.model.JewleryDTO;
import com.progdist.jewlery.model.inputs.JewleryInput;
import com.progdist.jewlery.services.JewleryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
        return service.getJewleries().stream()
                .map((Jewlery j) -> JewleryDTO.builder()
                        .id(j.getId())
                        .name(j.getName())
                        .type(j.getType())
                        .category(j.getCategory())
                        .price(j.getPrice())
                        .creationDate(j.getCreationDate())
                        .description(j.getDescription())
                        .materials(j.getMaterials())
                        .images(j.getImages().stream()
                                .map((byte[] img) -> Base64.getEncoder().encodeToString(img))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
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
    public Jewlery createJewlery(@RequestBody JewleryInput input) {
        return service.createJewlery(input);
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
}
