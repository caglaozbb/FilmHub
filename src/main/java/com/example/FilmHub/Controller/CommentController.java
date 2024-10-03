package com.example.FilmHub.Controller;

import com.example.FilmHub.Model.Request.CommentDto;
import com.example.FilmHub.Model.Comment;
import com.example.FilmHub.Model.Film;
import com.example.FilmHub.Model.User;
import com.example.FilmHub.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Tüm yorumları getir
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // ID'ye göre yorumu getir
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Kullanıcıya göre yorumları getir
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Long userId, @RequestBody User user) {
        List<Comment> comments = commentService.getCommentsByUser(user);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Filme göre yorumları getir
    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<Comment>> getCommentsByFilm(@PathVariable Long filmId, @RequestBody Film film) {
        List<Comment> comments = commentService.getCommentsByFilm(film);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Kullanıcı ve filme göre yorumları getir
    @GetMapping("/user/{userId}/film/{filmId}")
    public ResponseEntity<List<Comment>> getCommentsByUserAndFilm(@PathVariable Long userId, @PathVariable Long filmId, @RequestBody User user, @RequestBody Film film) {
        List<Comment> comments = commentService.getCommentsByUserAndFilm(user, film);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@Validated @RequestBody CommentDto commentDto, @RequestParam Long userId) {
        Comment createdComment = commentService.createComment(commentDto, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Yorumu güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @Validated @RequestBody Comment updatedComment) {
        try {
            Comment comment = commentService.updateComment(id, updatedComment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Yorumu sil
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}