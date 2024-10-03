package com.example.FilmHub.Model.Request;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilmDto {
    private Long id; // Film ID'si
    private String title; // Film başlığı
    private String description; // Film açıklaması
    private LocalDateTime releaseDate; // Çıkış tarihi
    private int rating; // Film puanı
    // Diğer gerekli alanları ekleyebilirsiniz
}
