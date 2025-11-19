package com.viniciusdev.spring_security_architecture.controllers;
import com.viniciusdev.spring_security_architecture.dtos.request.UpdateUserRequest;
import com.viniciusdev.spring_security_architecture.dtos.request.UserRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.UserResponse;
import com.viniciusdev.spring_security_architecture.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity<List<UserResponse>> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read') || @userAccessService.isOwner(#id, authentication)")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request, UriComponentsBuilder uriBuilder) {
        return userService.create(request, uriBuilder);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:write') || @userAccessService.isOwner(#id, authentication)")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:delete') || @userAccessService.isOwner(#id, authentication)")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        return userService.deleteById(id);
    }
}
