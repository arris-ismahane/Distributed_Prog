package com.progdist.jewlery.services;

import com.progdist.jewlery.excpetions.NotFoundException;
import com.progdist.jewlery.model.Jewlery;
import com.progdist.jewlery.model.JewleryDTO;
import com.progdist.jewlery.model.inputs.JewleryInput;
import com.progdist.jewlery.repositories.JewleryRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JewleryService {
    private final JewleryRepository repository;
    private final CategoryService categoryService;
    private final EmperiaUserService emperiaUserService;
    private final JwtService jwtService;

    public List<JewleryDTO> getJewleries() {
        return repository.findAll().stream()
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

    public Jewlery getJewleryById(long id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException("Not Found Exception","Jewlery not found"));
    }

    public void deleteJewlery(long id) {
        repository.deleteById(id);
    }

    public Jewlery createJewlery(JewleryInput input, HttpServletRequest request) {
        var jewlery = input.toEntity();
        var category = categoryService.getCategoryById(input.categoryId());
        jewlery.setCategory(category);

        // Extract JWT from the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token); // <-- We'll define this

        // Find the user
        var user = emperiaUserService.getEmperiaUserByUsername(username);

        // Assign user to the jewelry
        jewlery.setUser(user);

        return repository.save(jewlery);
    }  
    
    public Jewlery updateJewlery(long id, JewleryInput input) {
        var old_jewlery = getJewleryById(id);
        var new_jewlery = input.toEntity();
        var category = categoryService.getCategoryById(input.categoryId());
        new_jewlery.setCreationDate(old_jewlery.getCreationDate());
        new_jewlery.setId(old_jewlery.getId());
        new_jewlery.setCategory(category);
        new_jewlery.setUser(old_jewlery.getUser());
        return repository.save(new_jewlery);
    }

    public Jewlery updateJewleryImages(Long jewelryId, List<byte[]> images) {
        // Find existing jewelry
        Jewlery jewlery = repository.findById(jewelryId)
                .orElseThrow(() -> new EntityNotFoundException("Jewelry not found with id: " + jewelryId));

        // Update images
        jewlery.setImages(images);

        // Save and return
        return repository.save(jewlery);
    }

    public List<JewleryDTO> getJewleriesByUserOwnership(Long userId, boolean owned, Long categoryId, String sortBy, String order) {
        List<Jewlery> result;

        // Choose method based on ownership and category presence
        if (owned) {
            result = (categoryId != null) ? repository.findByUser_IdAndCategory_Id(userId, categoryId)
                    : repository.findByUser_Id(userId);
        } else {
            result = (categoryId != null) ? repository.findByUser_IdNotAndCategory_Id(userId, categoryId)
                    : repository.findByUser_IdNot(userId);
        }

        // Sorting
        Comparator<Jewlery> comparator = switch (sortBy) {
            case "price" -> Comparator.comparing(Jewlery::getPrice);
            case "creationDate" -> Comparator.comparing(Jewlery::getCreationDate);
            default -> Comparator.comparing(Jewlery::getCreationDate);
        };

        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        result.sort(comparator);

        // Map to DTO
        return result.stream()
                .map(j -> JewleryDTO.builder()
                        .id(j.getId())
                        .name(j.getName())
                        .type(j.getType())
                        .category(j.getCategory())
                        .price(j.getPrice())
                        .creationDate(j.getCreationDate())
                        .description(j.getDescription())
                        .materials(j.getMaterials())
                        .images(j.getImages().stream()
                                .map(img -> Base64.getEncoder().encodeToString(img))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
