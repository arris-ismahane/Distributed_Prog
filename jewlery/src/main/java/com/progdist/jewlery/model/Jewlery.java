package com.progdist.jewlery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jewlery {
    private String name;
    private String type;
    private Category category;
    private double price;
    private long creationDate;
    private String description;
}
