package com.example.FilmHub.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Film cannot be null")
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @NotNull(message = "Comment cannot be null")
    private String comment;

    @NotNull(message = "Creation date cannot be null")
    private Date createdAt;
}