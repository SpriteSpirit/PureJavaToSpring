package main.java.com;

import main.java.com.model.User;

public class Main {
    public static void main(String[] args) {
        User user = new User("a.backer@yandex.ru", "admin", "Angelina");
        System.out.println(user.getEmail());
    }
}