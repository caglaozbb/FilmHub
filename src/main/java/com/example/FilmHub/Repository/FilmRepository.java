package com.example.FilmHub.Repository;

import com.example.FilmHub.Entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    // Film başlığına göre film arama
    List<Film> findByTitleContainingIgnoreCase(String title);

    // Belirli bir rating üzerindeki filmleri bulma
    List<Film> findByRatingGreaterThanEqual(Double rating);
}