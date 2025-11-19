package com.viniciusdev.spring_security_architecture.dtos.request;

import java.util.UUID;

public record CreatePostRequest(String title, String content, UUID authorId) {
}
