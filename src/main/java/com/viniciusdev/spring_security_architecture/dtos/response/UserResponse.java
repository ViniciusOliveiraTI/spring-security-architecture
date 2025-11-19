package com.viniciusdev.spring_security_architecture.dtos.response;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email) {
}
