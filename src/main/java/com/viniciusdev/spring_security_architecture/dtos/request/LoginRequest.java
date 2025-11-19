package com.viniciusdev.spring_security_architecture.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@Email @NotBlank(message = "Email must be not empty") String email,
                           @NotBlank(message = "Password must be not empty") String password) {
}
