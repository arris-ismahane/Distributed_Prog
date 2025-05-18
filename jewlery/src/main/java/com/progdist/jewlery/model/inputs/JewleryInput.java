package com.progdist.jewlery.model.inputs;

import java.util.List;

import com.progdist.jewlery.model.Jewlery;

public record JewleryInput(
                String name,
                String type,
                long categoryId,
                double price,
                long creationDate,
                String description,
                List<String> materials,
                List<String> images) {
        public Jewlery toEntity() {
                return new Jewlery(
                                name,
                                type,
                                null,
                                price,
                                creationDate,
                                description,
                                materials,
                                null, null);
        }
}