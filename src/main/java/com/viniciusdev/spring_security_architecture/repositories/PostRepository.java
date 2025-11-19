package com.viniciusdev.spring_security_architecture.repositories;

import com.viniciusdev.spring_security_architecture.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> { }
