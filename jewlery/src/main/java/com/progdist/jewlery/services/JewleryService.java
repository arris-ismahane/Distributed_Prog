package com.progdist.jewlery.services;

import com.progdist.jewlery.model.Jewlery;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JewleryService {

    public List<Jewlery> getCategories() {
        return List.of();
    }

    public Jewlery getJewleryById(long id) {
        return new Jewlery();
    }

    public void deleteJewlery(long id) {
        
    }

    public Jewlery createJewlery(Jewlery input) {
        return new Jewlery();
    }    
    
    public Jewlery updateJewlery(Jewlery input) {
        return new Jewlery();
    }
}
