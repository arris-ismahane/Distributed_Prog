package com.progdist.jewlery.model;

public record JewleryFilter(
        Long userId,
        boolean owned,
        Long categoryId,
        String sortBy, 
        boolean ascending) {
}
