package com.example.user_api;


import com.example.user_api.exception.UserNotFoundException;
import com.example.user_api.security.JwtRequestFilter;
import com.example.user_api.security.JwtUtil;
import com.example.user_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // Mock the correct dependency now

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private JwtUtil jwtUtil;
    @Test
    public void testGetAllUsers() throws Exception {
        User user = new User(1L, "Aparna", "aparna@example.com");
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Aparna"))
                .andExpect(jsonPath("$[0].email").value("aparna@example.com"));
    }

    @Test
    public void testGetUserById_found() throws Exception {
        User user = new User(1L, "Aparna", "aparna@example.com");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aparna"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-users.href").exists());
    }

    @Test
    @WithMockUser
    public void testGetUserById_notFound() throws Exception {
        when(userService.getUserById(99L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));

    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User(null, "Alice", "alice@example.com");
        User saved = new User(1L, "Alice", "alice@example.com");

        when(userService.createUser(Mockito.any(User.class))).thenReturn(saved);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Long id = 1L;
        User updated = new User(id, "New", "new@example.com");

        when(userService.updateUser(Mockito.eq(id), Mockito.any(User.class))).thenReturn(updated);

        mockMvc.perform(put("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}
