package com.progdist.emperia.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jewlery{
    private long id;
    private String name;
    private String type;
    private Category category;
    private double price;
    private long creationDate;
    private String description;
    private List<String> materials;
    private List<String> images;
}
