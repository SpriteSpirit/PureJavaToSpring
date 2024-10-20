package com.habitapp.service;

import com.habitapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new InMemoryUserService();
    }

    @Test
    void testRegisterUser() {
        User user = userService.register("Борис", "boris@example.com", "boris_password");
        assertNotNull(user);
        assertEquals("Борис", user.getName());
        assertEquals("boris@example.com", user.getEmail());
    }

    @Test
    void testRegisterUserWithExistingEmail() {
        userService.register("Борис", "boris@example.com", "boris_password");
        assertThrows(IllegalArgumentException.class, () -> {
            userService.register("Барбарис", "boris@example.com", "boris_password");
        });
    }

    @Test
    void testLoginSuccess() {
        userService.register("Борис", "boris@example.com", "boris_password");
        User loggedUser = userService.login("boris@example.com", "boris_password");
        assertNotNull(loggedUser);
        assertEquals("Борис", loggedUser.getName());
    }

    @Test
    void testLoginFailure() {
        userService.register("Борис", "boris@example.com", "boris_password");
        User loggedUser = userService.login("boris@example.com", "boris_password_wrong");
        assertNull(loggedUser);
    }

    @Test
    void testUpdateProfile() {
        User user = userService.register("Борис", "boris@example.com", "boris_password");
        User updatedUser = userService.updateProfile(user.getId(), "Барбара", "barbara@example.com", "barbara_password");
        assertEquals("Барбара", updatedUser.getName());
        assertEquals("barbara@example.com", updatedUser.getEmail());
    }

    @Test
    void testUpdateProfileWithNonExistingUser() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateProfile("несуществующий_id_в_виде_строки", "Борис", "boris@example.com", "boris_password");
        });
    }

    @Test
    void testDeleteAccount() {
        User user = userService.register("Борис", "boris@example.com", "boris_password");
        boolean deleted = userService.deleteAccount(user.getId());
        assertTrue(deleted);
        assertNull(userService.getUserById(user.getId()));
    }

    @Test
    void testDeleteNonExistingAccount() {
        boolean deleted = userService.deleteAccount("несуществующий_id_в_виде_строки");
        assertFalse(deleted);
    }

    @Test
    void testGetUserById() {
        User user = userService.register("Борис", "boris@example.com", "boris_password");
        User retrievedUser = userService.getUserById(user.getId());
        assertNotNull(retrievedUser);
        assertEquals("Борис", retrievedUser.getName());
    }
}
