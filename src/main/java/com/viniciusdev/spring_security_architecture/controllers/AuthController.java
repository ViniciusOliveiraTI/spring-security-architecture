package com.viniciusdev.spring_security_architecture.controllers;

import com.viniciusdev.spring_security_architecture.dtos.request.LoginRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.LoginResponse;
import com.viniciusdev.spring_security_architecture.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
