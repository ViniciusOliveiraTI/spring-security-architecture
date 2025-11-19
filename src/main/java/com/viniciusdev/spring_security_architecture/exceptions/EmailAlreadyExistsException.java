package com.viniciusdev.spring_security_architecture.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {

    private HttpStatus status;
    private String code;

    public EmailAlreadyExistsException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public EmailAlreadyExistsException(String message) {
        this(message, "BAD_REQUEST", HttpStatus.BAD_REQUEST);
    }
}
