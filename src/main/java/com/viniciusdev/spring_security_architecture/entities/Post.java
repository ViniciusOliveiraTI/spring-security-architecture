package com.viniciusdev.spring_security_architecture.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "publish_date")
    private Instant publishDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Post(UUID id, String title, String content, Instant publishDate, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.author = author;
    }
}
