package main.java.com.service;
import main.java.com.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация UserService для управления пользователями в памяти.
 * - `register`: регистрация нового пользователя.
 * - `login`: авторизация пользователя.
 * - `updateProfile`: обновление профиля пользователя.
 * - `deleteAccount`: удаление пользователя.
 * - `getUserById`: получение пользователя по идентификатору.
 */
public class InMemoryUserService implements UserService {
    private final Map<String, User> users = new HashMap<>();

    /**
     * Регистрирует нового пользователя.
     *
     * @param name Имя пользователя.
     * @param email Email пользователя.
     * @param password Пароль пользователя.
     * @return Зарегистрированный пользователь.
     * @throws IllegalArgumentException если email уже занят.
     */
    @Override
    public User register(String name, String email, String password) {
        if (users.values().stream().anyMatch(user -> user.getEmail().equals(email))) {
            throw new IllegalArgumentException("Такой email уже существует");
        }
        User user = new User(email, password, name);
        users.put(user.getId(), user);
        System.out.println(users);
        return user;
    }

    /**
     * Авторизует пользователя.
     *
     * @param email Email пользователя.
     * @param password Пароль пользователя.
     * @return Пользователь или {@code null}, если неверные данные.
     */
    @Override
    public User login(String email, String password) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    /**
     * Обновляет профиль пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param name Новое имя пользователя.
     * @param email Новый email пользователя.
     * @param password Новый пароль пользователя.
     * @return Обновленный пользователь.
     * @throws IllegalArgumentException если пользователь не найден.
     */
    @Override
    public User updateProfile(String userId, String name, String email, String password) {
        User user = users.get(userId);

        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    /**
     * Удаляет пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return {@code true} если удаление прошло успешно, {@code false} иначе.
     */
    @Override
    public boolean deleteAccount(String userId) {
        return users.remove(userId) != null;
    }

    @Override
    public User getUserById(String userId) {
        return null;
    }

}
