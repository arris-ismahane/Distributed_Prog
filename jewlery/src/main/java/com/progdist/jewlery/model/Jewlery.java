package com.progdist.jewlery.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Jewlery extends BasicEntity {
    private String name;
    private String type;
    @ManyToOne
    private Category category;
    private double price;
    private long creationDate;
    private String description;
    private List<String> materials;
    @Lob
    @ElementCollection(fetch = FetchType.EAGER)
    private List<byte[]> images;
    @ManyToOne(optional = false) 
    private EmperiaUser user;
}
