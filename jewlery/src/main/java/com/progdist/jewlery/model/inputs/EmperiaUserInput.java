package com.progdist.jewlery.model.inputs;

import com.progdist.jewlery.model.EmperiaUserType;
import com.progdist.jewlery.model.EmperiaUser;  

public record EmperiaUserInput(
    String firstName,
    String lastName,
    String username,
    String password,
    long birthDate,
    EmperiaUserType userType 
) {
    public EmperiaUser toEntity() {
        return new EmperiaUser(
            firstName,
            lastName,
            username,
            password,
            birthDate,
            userType
        );
    }
}