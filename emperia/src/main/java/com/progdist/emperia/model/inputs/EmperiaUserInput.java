package com.progdist.emperia.model.inputs;

import com.progdist.emperia.model.EmperiaUserType;

public record EmperiaUserInput(
    String firstName,
    String lastName,
    String username,
    String password,
    long birthDate,
    EmperiaUserType userType 
) {
    
}