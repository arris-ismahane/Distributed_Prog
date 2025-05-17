package com.progdist.jewlery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class EmperiaUser extends BasicEntity {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    private String password;
    private long birthDate;
    @Enumerated(EnumType.STRING)
    private EmperiaUserType emperiaUserType;
}
