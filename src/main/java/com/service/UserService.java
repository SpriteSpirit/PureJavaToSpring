package main.java.com.service;

import main.java.com.model.User;

/**
 * Интерфейс для управления пользователями, предоставляющий следующие методы:
 * - `register`: регистрация нового пользователя.
 * - `login`: авторизация пользователя.
 * - `updateProfile`: обновление профиля пользователя.
 * - `deleteAccount`: удаление аккаунта пользователя.
 * - `getUserById`: получение пользователя по его идентификатору.
 */
public interface UserService {
    User register(String name, String email, String password);
    User login(String email, String password);
    User updateProfile(String userId, String name, String email, String password);
    boolean deleteAccount(String userId);
    User getUserById(String userId);
}
