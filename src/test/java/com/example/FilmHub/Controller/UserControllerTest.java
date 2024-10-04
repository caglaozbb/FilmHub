package com.example.FilmHub.Controller;

import com.example.FilmHub.Model.Request.UserDto;
import com.example.FilmHub.Model.User;
import com.example.FilmHub.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("testUser"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testUser"));

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserByEmail() throws Exception {
        String email = "testUser@example.com";
        User user = new User();
        user.setEmail(email);
        user.setUsername("testUser");

        when(userService.getUserByEmail(email)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/email").param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testUser"));

        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testGetUserByEmail_NotFound() throws Exception {
        String email = "nonexistent@example.com";
        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/email").param("email", email))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("testUser@example.com");
        userDto.setPassword("password123");

        User createdUser = new User();
        createdUser.setUsername(userDto.getUsername());
        createdUser.setEmail(userDto.getEmail());

        when(userService.createUser(any(UserDto.class))).thenReturn(createdUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"email\":\"testUser@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testUser"));

        verify(userService, times(1)).createUser(any(UserDto.class));
    }



}
