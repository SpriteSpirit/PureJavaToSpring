package com.habitapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // JUnit 5
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.habitapp.model.User;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new InMemoryUserService();
    }

    @Test
    void testRegisterUser() {
        User user = userService.register("NewUser", "new@example.com", "newpassword");
        assertNotNull(user);
        assertEquals("NewUser", user.getName());
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    void testLoginSuccess() {
        userService.register("LoginUser", "login@example.com", "loginpassword");
        User loggedUser = userService.login("login@example.com", "loginpassword");
        assertNotNull(loggedUser);
    }

    @Test
    void testLoginFail() {
        User loggedUser = userService.login("nonexistent@example.com", "wrongpassword");
        assertNull(loggedUser);
    }

    @Test
    void testUpdateProfile() {
        User user = userService.register("OriginalName", "original@example.com", "originalpassword");
        String userId = user.getId();

        User updatedUser = userService.updateProfile(userId, "UpdatedName", "updated@example.com", "updatedpassword");
        assertEquals("UpdatedName", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteAccount() {
        User user = userService.register("DeleteUser", "delete@example.com", "deletepassword");
        String userId = user.getId();

        boolean isDeleted = userService.deleteAccount(userId);
        assertTrue(isDeleted);
        User userAfterDelete = userService.getUserById(userId);
        assertNull(userAfterDelete);
    }

    @Test
    void testGetUserById() {
        User user = userService.register("TestUser", "test@example.com", "password");
        String userId = user.getId();

        User fetchedUser = userService.getUserById(userId);
        assertNotNull(fetchedUser);
        assertEquals("TestUser", fetchedUser.getName());
    }
}
