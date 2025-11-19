package com.viniciusdev.spring_security_architecture.controllers;
import com.viniciusdev.spring_security_architecture.dtos.request.CreatePostRequest;
import com.viniciusdev.spring_security_architecture.dtos.request.UpdatePostRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.PostResponse;
import com.viniciusdev.spring_security_architecture.dtos.response.UserResponse;
import com.viniciusdev.spring_security_architecture.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable UUID id) {
        return postService.findById(id);
    }

    @GetMapping("/{id}/author")
    public ResponseEntity<UserResponse> findAuthor(@PathVariable UUID id) {
        return postService.findAuthor(id);
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody CreatePostRequest request, UriComponentsBuilder uriBuilder) {
        return postService.create(request, uriBuilder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdatePostRequest request) {
        return postService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        return postService.deleteById(id);
    }
}
