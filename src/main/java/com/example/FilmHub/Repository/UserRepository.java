package com.example.FilmHub.Repository;

import com.example.FilmHub.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Email ile kullanıcı bulmak için custom bir method
    Optional<User> findByEmail(String email);

    // Username ile kullanıcı bulmak için custom bir method
    Optional<User> findByUsername(String username);

    // Kullanıcı olup olmadığını kontrol etmek için custom bir method
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}