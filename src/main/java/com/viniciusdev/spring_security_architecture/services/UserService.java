package com.viniciusdev.spring_security_architecture.services;

import com.viniciusdev.spring_security_architecture.dtos.request.UpdateUserRequest;
import com.viniciusdev.spring_security_architecture.dtos.request.UserRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.PostResponse;
import com.viniciusdev.spring_security_architecture.dtos.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ResponseEntity<List<UserResponse>> findAll();
    ResponseEntity<UserResponse> findById(UUID id);
    ResponseEntity<UserResponse> create(UserRequest request, UriComponentsBuilder uriBuilder);
    ResponseEntity<UserResponse> update(UUID id, UpdateUserRequest request);
    ResponseEntity<Void> deleteById(UUID id);
    ResponseEntity<UserResponse> me(Authentication authentication);
    ResponseEntity<List<PostResponse>> findPosts(UUID id);
}
