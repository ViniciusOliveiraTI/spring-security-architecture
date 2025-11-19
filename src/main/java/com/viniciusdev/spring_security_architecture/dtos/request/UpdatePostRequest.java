package com.viniciusdev.spring_security_architecture.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostRequest(@NotBlank(message = "Title must be not empty") String title,
                                @NotBlank(message = "Content must be not empty") String content) { }
