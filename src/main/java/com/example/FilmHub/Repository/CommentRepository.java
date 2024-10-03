package com.example.FilmHub.Repository;

import com.example.FilmHub.Model.Comment;
import com.example.FilmHub.Model.Film;
import com.example.FilmHub.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Belirli bir kullanıcıya ait yorumları bulma
    List<Comment> findByUser(User user);

    // Belirli bir film ile ilgili yorumları bulma
    List<Comment> findByFilm(Film film);

    // Belirli bir kullanıcının belirli bir film için yaptığı yorumları bulma
    List<Comment> findByUserAndFilm(User user, Film film);
}