package com.viniciusdev.spring_security_architecture.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserRequest(@NotBlank(message = "Name must be not empty") String name,
                          @NotBlank(message = "Email must be not empty") @Email String email,
                          @NotBlank(message = "Password must be not empty") @Size(min = 8, message = "Password must be at last 8 chars") String password) { }
