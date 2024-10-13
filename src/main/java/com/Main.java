package main.java.com;

import main.java.com.model.User;
import main.java.com.service.InMemoryUserService;
import main.java.com.service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new InMemoryUserService();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        boolean isAuth = false;

        while (true) {
            if (!isAuth) {
                System.out.println("Добро пожаловать в трекер привычек!");
                System.out.println("Выберите действие:");
                System.out.println("1. Регистрация");
                System.out.println("2. Вход");
                System.out.println("0. Выход");

                System.out.println("Введите число: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Введите имя:");
                        String name = scanner.nextLine();
                        System.out.println("Введите email:");
                        String email = scanner.nextLine();
                        System.out.println("Введите пароль:");
                        String password = scanner.nextLine();

                        try {
                            User registeredUser = userService.register(name, email, password);
                            System.out.println("Создан новый пользователь: " + registeredUser.getName());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.println("Введите email:");
                        email = scanner.nextLine();
                        System.out.println("Введите пароль:");
                        password = scanner.nextLine();

                        User loggedUser = userService.login(email, password);
                        if (loggedUser != null) {
                            System.out.println("Вы успешно авторизовались!");
                            currentUser = loggedUser;
                            isAuth = true;
                        } else {
                            System.out.println("Неверный логин или пароль");
                        }
                        break;

                    case 0:
                        System.out.println("До свидания!");

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                        break;
                }
            } else {
                System.out.println("Добро пожаловать в трекер привычек " + currentUser.getName());
                System.out.println("Выберите действие:");
                System.out.println("1. Обновить профиль");
                System.out.println("2. Удалить аккаунт");
                System.out.println("0. Выход");

                System.out.println("Введите число: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Выберите, что хотите изменить:");
                        System.out.println("1. Изменить имя");
                        System.out.println("2. Изменить email");
                        System.out.println("3. Изменить пароль");
                        System.out.println("0. Назад");

                        System.out.println("Введите число: ");
                        int choiceChanged = scanner.nextInt();
                        scanner.nextLine();

                        switch (choiceChanged) {
                            case 1:
                                System.out.println("Введите новое имя:");
                                User updatedUserName = userService.updateProfile(currentUser.getId(), scanner.nextLine(), currentUser.getEmail(), currentUser.getPassword());

                                if (updatedUserName != null) {
                                    currentUser = updatedUserName;
                                    System.out.println("Профиль обновлен: " + "[name: " + currentUser.getName() + ", email: " + currentUser.getEmail() + ", password: " + currentUser.getPassword() + "]");
                                } else {
                                    System.out.println("Ошибка при изменении имени");
                                }
                                break;

                            case 2:
                                System.out.println("Введите новый email:");
                                User updatedUserEmail = userService.updateProfile(currentUser.getId(), currentUser.getEmail(), scanner.nextLine(), currentUser.getPassword());

                                if (updatedUserEmail != null) {
                                    currentUser = updatedUserEmail;
                                    System.out.println("Профиль обновлен: " + "[name: " + currentUser.getName() + ", email: " + currentUser.getEmail() + ", password: " + currentUser.getPassword() + "]");
                                } else {
                                    System.out.println("Ошибка при изменении имени");
                                }
                                break;

                            case 3:
                                System.out.println("Введите новый пароль:");
                                User updatedUserPassword = userService.updateProfile(currentUser.getId(), currentUser.getEmail(), currentUser.getPassword(), scanner.nextLine());

                                if (updatedUserPassword != null) {
                                    currentUser = updatedUserPassword;
                                    System.out.println("Профиль обновлен: " + "[name: " + currentUser.getName() + ", email: " + currentUser.getEmail() + ", password: " + currentUser.getPassword() + "]");
                                } else {
                                    System.out.println("Ошибка при изменении имени");
                                }
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                                break;
                        }
                        break;

                    case 2:
                        System.out.println("Вы уверены, что хотите удалить аккаунт? Да/Нет");
                        String userDeleteAnswer = scanner.nextLine();
                        boolean isDeleted = false;

                        if (userDeleteAnswer.equalsIgnoreCase("да")) {
                            isDeleted = userService.deleteAccount(currentUser.getId());
                        }

                        if (isDeleted) {
                            System.out.println("Профиль пользователя успешно удален");
                            isAuth = false;
                        }
                        break;

                    case 0:
                        System.out.println("Выход из программы");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                        break;
                }
            }
        }
    }
}