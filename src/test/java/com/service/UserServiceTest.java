package test.java.com.service;

import main.java.com.model.User;
import main.java.com.service.InMemoryUserService;
import main.java.com.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new InMemoryUserService();
    }

    @Test
    void testRegisterUser() {
        User user = userService.register("John Doe", "john@example.com", "password123");
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testLoginSuccess() {
        userService.register("LoginUser", "login@example.com", "loginpassword");
        User loggedUser = userService.login("login@example.com", "loginpassword");
        assertNotNull(loggedUser);
        assertEquals("LoginUser", loggedUser.getName());
    }

    @Test
    void testLoginFail() {
        User loggedUser = userService.login("nonexistent@example.com", "wrongpassword");
        assertNull(loggedUser);
    }

    @Test
    void testUpdateProfile() {
        User user = userService.register("UpdatedName", "updated@example.com", "updatedpassword");
        User updatedUser = userService.updateProfile(user.getId(), "UpdatedName", "updated@example.com", "updatedpassword");
        assertEquals("UpdatedName", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteAccount() {
        User user = userService.register("DeletedName", "deleted@example.com", "deletedPassword");
        boolean isDeleted = userService.deleteAccount(user.getId());
        assertTrue(isDeleted);

        User userAfterDelete = userService.getUserById(user.getId());
        assertNull(userAfterDelete);
    }

}