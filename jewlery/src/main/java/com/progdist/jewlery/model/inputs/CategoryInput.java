package com.progdist.jewlery.model.inputs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.progdist.jewlery.model.Category;

public record CategoryInput(String name) {
    @JsonIgnore
    public Category toEntity() {
        return new Category(name);
    }
}