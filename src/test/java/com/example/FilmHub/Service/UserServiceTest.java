package com.example.FilmHub.Service;

import com.example.FilmHub.Model.Request.UserDto;
import com.example.FilmHub.Model.User;
import com.example.FilmHub.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito'yu başlatır
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");

        // Mock repository davranışı
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Servis metodunu test ediyoruz
        Optional<User> result = userService.getUserById(userId);

        // Sonuçları kontrol edelim
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).findById(userId);
    }
    @Test
    void testGetUserByEmail() {
        String email = "testUser@example.com";
        User user = new User();
        user.setEmail(email);
        user.setUsername("testUser");

        // Mock repository davranışı
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Servis metodunu test ediyoruz
        Optional<User> result = userService.getUserByEmail(email);

        // Sonuçları kontrol edelim
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetUserByEmail_NotFound() {
        String email = "nonexistent@example.com";

        // Mock repository davranışı (boş dönecek)
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Servis metodunu test ediyoruz
        Optional<User> result = userService.getUserByEmail(email);

        // Kullanıcı bulunamadığı için boş döndüğünü kontrol edelim
        assertFalse(result.isPresent());

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).findByEmail(email);
    }
    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUser");
        existingUser.setEmail("oldUser@example.com");

        User updatedUser = new User();
        updatedUser.setUsername("newUser");
        updatedUser.setEmail("newUser@example.com");
        updatedUser.setPassword("newPassword");

        // Mock repository findById ve save davranışı
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        // Servis metodunu test ediyoruz
        User result = userService.updateUser(userId, updatedUser);

        // Sonuçları kontrol edelim
        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        assertEquals("newUser@example.com", result.getEmail());

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_NotFound() {
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setUsername("newUser");
        updatedUser.setEmail("newUser@example.com");

        // Mock repository findById (boş dönecek)
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Kullanıcı bulunamadığında hata fırlatıldığını kontrol ediyoruz
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });

        assertEquals("User not found.", exception.getMessage());

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class)); // save çağrılmadığından emin ol
    }
    @Test
    void testDeleteUser() {
        Long userId = 1L;

        // Mock repository existsById ve deleteById davranışı
        when(userRepository.existsById(userId)).thenReturn(true);

        // Servis metodunu test ediyoruz
        userService.deleteUser(userId);

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        Long userId = 1L;

        // Mock repository existsById (false dönecek)
        when(userRepository.existsById(userId)).thenReturn(false);

        // Kullanıcı bulunamadığında hata fırlatıldığını kontrol ediyoruz
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found.", exception.getMessage());

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId); // deleteById çağrılmadığından emin ol
    }
    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("testUser@example.com");
        userDto.setPassword("password123");
        userDto.setRole("USER");

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setRole(userDto.getRole());

        // Mock repository save davranışı
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Servis metodunu test ediyoruz
        User createdUser = userService.createUser(userDto);

        // Sonuçları kontrol edelim
        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        assertEquals("testUser@example.com", createdUser.getEmail());
        assertNotEquals("password123", createdUser.getPassword()); // Şifrenin hashlenmiş olduğunu kontrol et

        // Repository'nin doğru şekilde çağrıldığını doğrula
        verify(userRepository, times(1)).save(any(User.class));
    }


}
