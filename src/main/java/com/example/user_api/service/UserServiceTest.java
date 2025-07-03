package com.example.user_api.service;

import com.example.user_api.User;
import com.example.user_api.UserRepository;
import com.example.user_api.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepository.class);
        userService = new UserService(userRepo);
    }

    @Test
    void testGetUserById_found() {
        User user = new User(1L, "Aparna", "aparna@example.com");
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);
        assertEquals("Aparna", result.getName());
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(99L);
        });
    }

    @Test
    void testCreateUser() {
        User user = new User(null, "Alice", "alice@example.com");
        User savedUser = new User(1L, "Alice", "alice@example.com");

        when(userRepo.save(user)).thenReturn(savedUser);

        User result = userService.createUser(user);
        assertEquals(1L, result.getId());
        assertEquals("Alice", result.getName());
    }

    @Test
    void testGetAllUsers() {
        List<User> mockList = List.of(new User(1L, "Bob", "bob@example.com"));
        when(userRepo.findAll()).thenReturn(mockList);

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).getName());
    }
}
