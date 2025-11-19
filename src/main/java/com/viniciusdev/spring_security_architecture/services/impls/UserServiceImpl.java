package com.viniciusdev.spring_security_architecture.services.impls;

import com.viniciusdev.spring_security_architecture.dtos.request.UpdateUserRequest;
import com.viniciusdev.spring_security_architecture.dtos.request.UserRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.PostResponse;
import com.viniciusdev.spring_security_architecture.dtos.response.UserResponse;
import com.viniciusdev.spring_security_architecture.entities.Role;
import com.viniciusdev.spring_security_architecture.entities.User;
import com.viniciusdev.spring_security_architecture.exceptions.EmailAlreadyExistsException;
import com.viniciusdev.spring_security_architecture.exceptions.NotFoundException;
import com.viniciusdev.spring_security_architecture.repositories.PostRepository;
import com.viniciusdev.spring_security_architecture.repositories.RoleRepository;
import com.viniciusdev.spring_security_architecture.repositories.UserRepository;
import com.viniciusdev.spring_security_architecture.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok().body(userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()))
                .toList());
    }

    @Override
    public ResponseEntity<UserResponse> findById(UUID id) {;

        User user = getUserOrThrow(id);

        return ResponseEntity.ok().body(new UserResponse(user.getId(), user.getName(), user.getEmail()));

    }

    @Transactional
    @Override
    public ResponseEntity<UserResponse> create(UserRequest request, UriComponentsBuilder uriBuilder) {

        if (userRepository.existsByEmail(request.email())) throw new EmailAlreadyExistsException("Invalid or existent email");

        User newUser = new User();

        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        Role role = getRoleOrThrow("USER");

        newUser.setRoles(Set.of(role));

        User savedUser = userRepository.save(newUser);

        URI location = uriBuilder
                .path("/user/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail()));
    }

    @Transactional
    @Override
    public ResponseEntity<UserResponse> update(UUID id, UpdateUserRequest request) {

        User user = getUserOrThrow(id);

        user.setName(request.name());
        user.setEmail(request.email());

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok().body(new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail()));
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteById(UUID id) {

        if (postRepository.existsByAuthorId(id)) throw new DataIntegrityViolationException("It is not possible to delete the user with id '%s'. This user has related data in other entities".formatted(id));

        if (!userRepository.existsById(id)) throw new NotFoundException("User with id '%s' not found".formatted(id));

        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserResponse> me(Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        User user = getUserOrThrow(userId);

        return ResponseEntity.ok().body(new UserResponse(user.getId(), user.getName(), user.getEmail()));
    }

    @Override
    public ResponseEntity<List<PostResponse>> findPosts(UUID id) {

        User user = getUserOrThrow(id);

        return ResponseEntity.ok().body(
                user.getPosts()
                        .stream()
                        .map(post ->
                                new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getPublishDate(),
                                new UserResponse(post.getAuthor().getId(), post.getAuthor().getName(), post.getAuthor().getEmail())))
                        .toList());

    }

    private User getUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id '%s' not found".formatted(id)));
    }

    private Role getRoleOrThrow(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Role with name '%s' not found".formatted(name)));
    }
}
