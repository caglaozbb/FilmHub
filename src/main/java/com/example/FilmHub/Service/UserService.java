package com.example.FilmHub.Service;

import com.example.FilmHub.Model.User;
import com.example.FilmHub.Model.Request.UserDto;
//import com.example.FilmHub.Exception.ValidationException; // Özel istisna sınıfını kullanmak için eklenmeli
import com.example.FilmHub.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
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

    // Kullanıcı oluşturma
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
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
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }
}

