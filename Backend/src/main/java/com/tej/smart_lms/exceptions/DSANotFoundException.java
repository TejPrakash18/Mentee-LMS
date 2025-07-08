package com.tej.smart_lms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DSANotFoundException extends RuntimeException {
    public DSANotFoundException(String message) {
        super(message);
    }
}
