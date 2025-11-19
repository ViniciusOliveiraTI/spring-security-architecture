package com.viniciusdev.spring_security_architecture.dtos.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreatePostRequest(@NotBlank(message = "Title must be not empty") String title,
                                @NotBlank(message = "Content must be not empty") String content,
                                @NotBlank(message = "Author id must be not empty") String authorId) {
}
