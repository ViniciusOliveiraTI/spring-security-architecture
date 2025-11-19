package com.viniciusdev.spring_security_architecture.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private HttpStatus status;
    private String code;

    public NotFoundException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public NotFoundException(String message) {
        this(message, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
