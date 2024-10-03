package com.example.FilmHub.Service;

import com.example.FilmHub.Entity.User;
//import com.example.FilmHub.Exception.ValidationException; // Özel istisna sınıfını kullanmak için eklenmeli
import com.example.FilmHub.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Tüm kullanıcıları getir
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ID'ye göre kullanıcıyı getir
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Kullanıcıyı email'e göre getir
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Kullanıcıyı username'e göre getir
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Yeni kullanıcı oluştur
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        // Şifreyi hashle
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Kullanıcıyı güncelle
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword()); // Şifrenin hashlenmiş olduğunu varsayıyorum.
            return userRepository.save(user);
        }).orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    // Kullanıcıyı sil
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }
}