package com.viniciusdev.spring_security_architecture.dtos.response;

import java.time.Instant;
import java.util.UUID;

public record PostResponse(UUID id, String title, String content, Instant publishDate, UserResponse author) {
}
