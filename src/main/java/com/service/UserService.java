package main.java.com.service;

import main.java.com.model.User;

/**
 * Интерфейс `UserService` определяет методы для управления пользователями в системе.
 * Предоставляет функциональность для регистрации, авторизации, обновления профиля,
 * удаления аккаунта и получения информации о пользователе.
 */

public interface UserService {
    User register(String name, String email, String password);
    User login(String email, String password);
    User updateProfile(String userId, String name, String email, String password);
    boolean deleteAccount(String userId);
    User getUserById(String userId);
}
