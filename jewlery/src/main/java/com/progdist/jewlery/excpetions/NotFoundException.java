package com.progdist.jewlery.excpetions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ExceptionBase {
    public NotFoundException(String title, String message) {
        super(title, message, HttpStatus.NOT_FOUND.value());
    }
}