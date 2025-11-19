package com.viniciusdev.spring_security_architecture.services.impls;

import com.viniciusdev.spring_security_architecture.dtos.request.CreatePostRequest;
import com.viniciusdev.spring_security_architecture.dtos.request.UpdatePostRequest;
import com.viniciusdev.spring_security_architecture.dtos.response.PostResponse;
import com.viniciusdev.spring_security_architecture.dtos.response.UserResponse;
import com.viniciusdev.spring_security_architecture.entities.Post;
import com.viniciusdev.spring_security_architecture.entities.User;
import com.viniciusdev.spring_security_architecture.repositories.PostRepository;
import com.viniciusdev.spring_security_architecture.repositories.UserRepository;
import com.viniciusdev.spring_security_architecture.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<PostResponse>> findAll() {
        return ResponseEntity.ok().body(
                postRepository.findAll()
                .stream()
                .map(post ->
                        new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getPublishDate(),
                        new UserResponse(post.getAuthor().getId(), post.getAuthor().getName(), post.getAuthor().getEmail())))
                .toList()
        );
    }

    @Override
    public ResponseEntity<PostResponse> findById(UUID id) {

        Post post = getPostOrThrow(id);

        return ResponseEntity.ok().body(
                new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getPublishDate(),
                new UserResponse(post.getAuthor().getId(), post.getAuthor().getName(), post.getAuthor().getEmail()))
        );

    }

    @Override
    public ResponseEntity<PostResponse> create(CreatePostRequest request, UriComponentsBuilder uriBuilder) {

        User author = getUserOrThrow(request.authorId());

        Post newPost = new Post();

        newPost.setTitle(request.title());
        newPost.setContent(request.content());
        newPost.setTitle(request.title());
        newPost.setPublishDate(Instant.now());
        newPost.setAuthor(author);

        Post savedPost = postRepository.save(newPost);

        URI location = uriBuilder
                .path("/post/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).body(
                new PostResponse(savedPost.getId(), savedPost.getTitle(), savedPost.getContent(), savedPost.getPublishDate(),
                new UserResponse(savedPost.getAuthor().getId(), savedPost.getAuthor().getName(), savedPost.getAuthor().getEmail()))
        );
    }

    @Override
    public ResponseEntity<PostResponse> update(UUID id, UpdatePostRequest request) {

        Post post = getPostOrThrow(id);

        post.setTitle(request.title());
        post.setContent(request.content());

        Post savedPost = postRepository.save(post);

        return ResponseEntity.ok().body(
                new PostResponse(savedPost.getId(), savedPost.getTitle(), savedPost.getContent(), savedPost.getPublishDate(),
                new UserResponse(savedPost.getAuthor().getId(), savedPost.getAuthor().getName(), savedPost.getAuthor().getEmail()))
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {

        if (postRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id '%s' not found".formatted(id));

        postRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserResponse> findAuthor(UUID id) {

        Post post = getPostOrThrow(id);

        User author = post.getAuthor();

        return ResponseEntity.ok().body(
                new UserResponse(author.getId(), author.getName(), author.getEmail())
        );
    }

    private Post getPostOrThrow(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id '%s' not found".formatted(id)));
    }

    private User getUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id '%s' not found".formatted(id)));
    }

}
