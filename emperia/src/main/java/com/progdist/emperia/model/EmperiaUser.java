package com.progdist.emperia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmperiaUser{
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private long birthDate;
    private EmperiaUserType EmperiaUserType;
}
