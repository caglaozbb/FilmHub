package com.example.FilmHub.Service;

import com.example.FilmHub.Model.Request.CommentDto;
import com.example.FilmHub.Model.Request.UserDto;
import com.example.FilmHub.Model.Request.FilmDto;
import com.example.FilmHub.Model.Comment;
import com.example.FilmHub.Model.Film;
import com.example.FilmHub.Model.User;
import com.example.FilmHub.Repository.CommentRepository;
import com.example.FilmHub.Repository.FilmRepository;
import com.example.FilmHub.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, FilmRepository filmRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(CommentDto commentDto, Long userId) {
        // Film nesnesini bul
        Film film = filmRepository.findById(commentDto.getFilmId())
                .orElseThrow(() -> new IllegalArgumentException("Film not found"));

        // Kullanıcıyı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Yeni yorum nesnesini oluştur
        Comment comment = new Comment();
        comment.setFilm(film);
        comment.setUser(user);
        comment.setComment(commentDto.getComment());

        // Yorumun kullanıcının ve filmin listesine eklenmesi
        film.getComments().add(comment);
        user.getComments().add(comment);

        // Yorumun kaydedilmesi
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