package com.example.FilmHub.Model.Request;


import lombok.Data;

@Data
public class UserDto {
    private Long id; // Kullanıcı ID'si
    private String username; // Kullanıcı adı
    private String email;
    private String password;
    private String role;
}