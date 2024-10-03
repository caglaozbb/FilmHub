package com.example.FilmHub.Service;

import com.example.FilmHub.Model.Film;
import com.example.FilmHub.Repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // Yeni film oluştur
    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    // Tüm filmleri getir
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    // ID'ye göre filmi getir
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    // Başlığa göre film ara
    public List<Film> searchFilmsByTitle(String title) {
        return filmRepository.findByTitleContainingIgnoreCase(title);
    }

    // Rating'e göre film ara
    public List<Film> findFilmsByRating(Double rating) {
        return filmRepository.findByRatingGreaterThanEqual(rating);
    }

    // Filmi güncelle
    public Film updateFilm(Long id, Film updatedFilm) {
        return filmRepository.findById(id).map(film -> {
            film.setTitle(updatedFilm.getTitle());
            film.setDescription(updatedFilm.getDescription());
            film.setReleaseDate(updatedFilm.getReleaseDate());
            film.setRating(updatedFilm.getRating());
            return filmRepository.save(film);
        }).orElseThrow(() -> new IllegalArgumentException("Film not found."));
    }

    // Filmi sil
    public void deleteFilm(Long id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Film not found.");
        }
    }
}