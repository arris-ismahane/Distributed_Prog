package com.progdist.jewlery.services;

import com.progdist.jewlery.excpetions.NotFoundException;
import com.progdist.jewlery.model.Jewlery;
import com.progdist.jewlery.model.inputs.JewleryInput;
import com.progdist.jewlery.repositories.JewleryRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JewleryService {
    private final JewleryRepository repository;
    private final CategoryService categoryService;

    public List<Jewlery> getJewleries() {
        return repository.findAll();
    }

    public Jewlery getJewleryById(long id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException("Not Found Exception","Jewlery not found"));
    }

    public void deleteJewlery(long id) {
        repository.deleteById(id);
    }

    public Jewlery createJewlery(JewleryInput input) {
        var jewlery = input.toEntity();
        var category = categoryService.getCategoryById(input.categoryId());
        jewlery.setCategory(category);
        return repository.save(jewlery);
    }    
    
    public Jewlery updateJewlery(long id, JewleryInput input) {
        var old_jewlery = getJewleryById(id);
        var new_jewlery = input.toEntity();
        var category = categoryService.getCategoryById(input.categoryId());
        new_jewlery.setCreationDate(old_jewlery.getCreationDate());
        new_jewlery.setId(old_jewlery.getId());
        new_jewlery.setCategory(category);
        return repository.save(new_jewlery);
    }
}
