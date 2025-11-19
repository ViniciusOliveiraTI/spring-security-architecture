package com.viniciusdev.spring_security_architecture.services;

import com.viniciusdev.spring_security_architecture.dtos.request.CreatePostRequest;
import com.viniciusdev.spring_security_architecture.dtos.request.UpdatePostRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.PostResponse;
import com.viniciusdev.spring_security_architecture.dtos.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

public interface PostService {
    ResponseEntity<List<PostResponse>> findAll();
    ResponseEntity<PostResponse> findById(UUID id);
    ResponseEntity<PostResponse> create(CreatePostRequest request, UriComponentsBuilder uriBuilder);
    ResponseEntity<PostResponse> update(UUID id, UpdatePostRequest request);
    ResponseEntity<Void> deleteById(UUID id);
    ResponseEntity<UserResponse> findAuthor(UUID id);
}
