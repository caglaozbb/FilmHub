package com.example.FilmHub.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.Date;

    @Entity
    @Table(name = "films")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Film {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull(message = "Title cannot be null")
        private String title;

        @NotNull(message = "Description cannot be null")
        private String description;

        @NotNull(message = "Release date cannot be null")
        private Date releaseDate;

        @Min(value = 0, message = "Rating must be positive")
        @Max(value = 10, message = "Rating cannot exceed 10")
        private Double rating;
    }

