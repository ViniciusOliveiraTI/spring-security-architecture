package com.viniciusdev.spring_security_architecture.dtos.request;

import com.viniciusdev.spring_security_architecture.entities.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UpdateUserRequest(@NotBlank(message = "Name must be not empty") String name,
                                @NotBlank(message = "Email must be not empty") String email) {
}
