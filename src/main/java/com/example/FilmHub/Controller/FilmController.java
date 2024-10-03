package com.example.FilmHub.Controller;


import com.example.FilmHub.Model.Film;
import com.example.FilmHub.Service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Tüm filmleri getir
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmService.getAllFilms();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    // ID'ye göre filmi getir
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        Optional<Film> film = filmService.getFilmById(id);
        return film.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Başlığa göre film ara
    @GetMapping("/search")
    public ResponseEntity<List<Film>> searchFilmsByTitle(@RequestParam String title) {
        List<Film> films = filmService.searchFilmsByTitle(title);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    // Rating'e göre film ara
    @GetMapping("/rating")
    public ResponseEntity<List<Film>> findFilmsByRating(@RequestParam Double rating) {
        List<Film> films = filmService.findFilmsByRating(rating);
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    // Yeni film oluştur
    @PostMapping
    public ResponseEntity<Film> createFilm(@Validated @RequestBody Film film) {
        Film createdFilm = filmService.createFilm(film);
        return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
    }

    // Filmi güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @Validated @RequestBody Film updatedFilm) {
        try {
            Film film = filmService.updateFilm(id, updatedFilm);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Filmi sil
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable Long id) {
        try {
            filmService.deleteFilm(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
