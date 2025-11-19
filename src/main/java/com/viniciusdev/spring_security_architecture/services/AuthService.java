package com.viniciusdev.spring_security_architecture.services;

import com.viniciusdev.spring_security_architecture.dtos.request.LoginRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<LoginResponse> login(LoginRequest request);

}
