package com.viniciusdev.spring_security_architecture.services.impls;

import com.viniciusdev.spring_security_architecture.dtos.request.LoginRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.LoginResponse;
import com.viniciusdev.spring_security_architecture.entities.Permission;
import com.viniciusdev.spring_security_architecture.entities.User;
import com.viniciusdev.spring_security_architecture.repositories.UserRepository;
import com.viniciusdev.spring_security_architecture.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request"));

        if (!isLoginCorrect(request.password(), user.getPassword())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");

        JwtClaimsSet claims = toClaims(user);

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok().body(new LoginResponse(token));
    }

    public boolean isLoginCorrect(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String getUserAuthorities(User user) {
        return user.getRoles()
                        .stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(Permission::getName)
                        .collect(Collectors.joining(" "));
    }

    public JwtClaimsSet toClaims(User user) {

        Instant now = Instant.now();
        long expiresIn = 300L;

        return JwtClaimsSet.builder()
                .issuer("backend")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scopes", getUserAuthorities(user))
                .build();
    }
}
