package com.progdist.emperia.model.inputs;

import java.util.List;

public record JewleryInput(
    String name,
    String type,
    long categoryId,
    double price,
    long creationDate,
    String description,
    List<String> materials,
    List<String> images
) {
    
    
}