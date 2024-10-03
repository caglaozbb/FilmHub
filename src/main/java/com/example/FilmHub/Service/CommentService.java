package com.example.FilmHub.Service;


import com.example.FilmHub.Entity.Comment;
import com.example.FilmHub.Entity.Film;
import com.example.FilmHub.Entity.User;
import com.example.FilmHub.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Yorum oluştur
    public Comment createComment(Comment comment) {
        comment.setCreatedAt(new Date()); // Yorum oluşturulma tarihini güncel tarih olarak ayarla
        return commentRepository.save(comment);
    }

    // Tüm yorumları getir
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // ID'ye göre yorum getir
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    // Kullanıcıya göre yorumları getir
    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findByUser(user);
    }

    // Filme göre yorumları getir
    public List<Comment> getCommentsByFilm(Film film) {
        return commentRepository.findByFilm(film);
    }

    // Belirli bir kullanıcı ve filme göre yorumları getir
    public List<Comment> getCommentsByUserAndFilm(User user, Film film) {
        return commentRepository.findByUserAndFilm(user, film);
    }

    // Yorum güncelle
    public Comment updateComment(Long id, Comment updatedComment) {
        return commentRepository.findById(id).map(comment -> {
            comment.setComment(updatedComment.getComment());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new IllegalArgumentException("Comment not found."));
    }

    // Yorum sil
    public void deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Comment not found.");
        }
    }
}