package com.example.FilmHub.Model.Request;

import lombok.Data;

@Data
public class CommentDto {
    private Long filmId; // Yalnızca film ID'si
    private UserDto user; // Kullanıcı bilgileri
    private String comment; // Yorum
}