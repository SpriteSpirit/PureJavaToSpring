package main.java.com.model;

import java.util.Objects;

/**
 * Класс User представляет собой модель пользователя.
 * Содержит поля email, пароль и имя пользователя.
 */

public class User {
    private String email;

    private String password;
    private String name;

    /**
     * Конструктор класса User.
     * Инициализирует email, пароль и имя пользователя.
     *
     * @param email    электронная почта
     * @param password пароль
     * @param name     имя пользователя
     */
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    /*
     * Возвращает электронную почту пользователя.
     */
    public String getEmail() {
        return email;
    }

    /*
     * Возвращает пароль пользователя.
     */
    public String getPassword() {
        return password;
    }

    /*
     * Возвращает имя пользователя.
     */
    public String getName() {
        return name;
    }

    /*
     * Устанавливает новое имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name);
    }
}
