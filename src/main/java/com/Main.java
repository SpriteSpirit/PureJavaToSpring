package main.java.com;

import main.java.com.model.Frequency;
import main.java.com.model.Habit;
import main.java.com.model.User;
import main.java.com.service.HabitService;
import main.java.com.service.InMemoryHabitService;
import main.java.com.service.InMemoryUserService;
import main.java.com.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Основной класс консольного приложения для управления пользователями и привычками.
 */
public class Main {
    public static void main(String[] args) {
        UserService userService = new InMemoryUserService();
        HabitService habitService = new InMemoryHabitService();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        boolean isAuth = false;

        while (true) {
            if (!isAuth) {
                System.out.println("\nДобро пожаловать в трекер привычек!");
                System.out.println("Выберите действие:");
                System.out.println("1. Регистрация");
                System.out.println("2. Вход");
                System.out.println("0. Выход");

                System.out.print("Введите число: ");
                int choice = getIntInput(scanner);

                switch (choice) {
                    case 1:
                        register(userService, scanner);
                        break;

                    case 2:
                        currentUser = login(userService, scanner);
                        if (currentUser != null) {
                            isAuth = true;
                        }
                        break;

                    case 0:
                        System.out.println("До свидания!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                        break;
                }
            } else {
                System.out.println("\nДобро пожаловать в трекер привычек, " + currentUser.getName() + "!");
                System.out.println("Выберите действие:");
                System.out.println("1. Управление привычками");
                System.out.println("2. Обновить профиль");
                System.out.println("3. Удалить аккаунт");
                System.out.println("0. Выйти из системы");

                System.out.print("Введите число: ");
                int choice = getIntInput(scanner);

                switch (choice) {
                    case 1:
                        manageHabits(habitService, currentUser, scanner);
                        break;

                    case 2:
                        updateProfile(userService, currentUser, scanner);
                        break;

                    case 3:
                        if (deleteAccount(userService, currentUser, scanner)) {
                            isAuth = false;
                            currentUser = null;
                        }
                        break;

                    case 0:
                        System.out.println("Вы вышли из системы.");
                        isAuth = false;
                        currentUser = null;
                        break;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                        break;
                }
            }
        }
    }

    /**
     * Обрабатывает регистрацию нового пользователя.
     */
    private static void register(UserService userService, Scanner scanner) {
        System.out.println("\n--- Регистрация ---");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            User registeredUser = userService.register(name, email, password);
            System.out.println("Создан новый пользователь: " + registeredUser.getName());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает вход пользователя в систему.
     */
    private static User login(UserService userService, Scanner scanner) {
        System.out.println("\n--- Вход ---");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        User loggedUser = userService.login(email, password);

        if (loggedUser != null) {
            System.out.println("Вы успешно авторизовались!");
            return loggedUser;
        } else {
            System.out.println("Неверный логин или пароль");
            return null;
        }
    }

    /**
     * Обрабатывает обновление профиля пользователя.
     */
    private static void updateProfile(UserService userService, User currentUser, Scanner scanner) {
        System.out.println("\n--- Обновление профиля ---");
        System.out.println("Выберите, что хотите изменить:");
        System.out.println("1. Изменить имя");
        System.out.println("2. Изменить email");
        System.out.println("3. Изменить пароль");
        System.out.println("0. Назад");

        System.out.print("Введите число: ");
        int choice = getIntInput(scanner);

        switch (choice) {
            case 1:
                System.out.print("Введите новое имя: ");
                String newName = scanner.nextLine();
                currentUser = userService.updateProfile(currentUser.getId(), newName, currentUser.getEmail(), currentUser.getPassword());
                System.out.println("Имя обновлено: " + currentUser.getName());
                break;

            case 2:
                System.out.print("Введите новый email: ");
                String newEmail = scanner.nextLine();
                currentUser = userService.updateProfile(currentUser.getId(), currentUser.getName(), newEmail, currentUser.getPassword());
                System.out.println("Email обновлён: " + currentUser.getEmail());
                break;

            case 3:
                System.out.print("Введите новый пароль: ");
                String newPassword = scanner.nextLine();
                currentUser = userService.updateProfile(currentUser.getId(), currentUser.getName(), currentUser.getEmail(), newPassword);
                System.out.println("Пароль обновлён.");
                break;

            case 0:
                // Назад
                break;

            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                break;
        }
    }

    /**
     * Обрабатывает удаление аккаунта пользователя.
     *
     * @return true, если аккаунт был удалён, иначе false.
     */
    private static boolean deleteAccount(UserService userService, User currentUser, Scanner scanner) {
        System.out.println("\n--- Удаление аккаунта ---");
        System.out.print("Вы уверены, что хотите удалить аккаунт? (да/нет): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("да")) {
            boolean isDeleted = userService.deleteAccount(currentUser.getId());
            if (isDeleted) {
                System.out.println("Аккаунт успешно удалён.");
                return true;
            } else {
                System.out.println("Ошибка при удалении аккаунта.");
            }
        } else {
            System.out.println("Удаление аккаунта отменено.");
        }
        return false;
    }

    /**
     * Обрабатывает управление привычками для аутентифицированного пользователя.
     */
    private static void manageHabits(HabitService habitService, User currentUser, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Управление привычками ---");
            System.out.println("1. Создать привычку");
            System.out.println("2. Просмотреть все привычки");
            System.out.println("3. Обновить привычку");
            System.out.println("4. Удалить привычку");
            System.out.println("5. Отметить привычку как выполненную");
            System.out.println("6. Просмотреть статистику привычки");
            System.out.println("0. Назад");

            System.out.print("Введите число: ");
            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    createHabit(habitService, currentUser, scanner);
                    break;

                case 2:
                    viewHabits(habitService, currentUser);
                    break;

                case 3:
                    updateHabit(habitService, currentUser, scanner);
                    break;

                case 4:
                    deleteHabit(habitService, currentUser, scanner);
                    break;

                case 5:
                    markHabitAsCompleted(habitService, currentUser, scanner);
                    break;

                case 6:
                    viewHabitStats(habitService, currentUser, scanner);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Создаёт новую привычку.
     */
    private static void createHabit(HabitService habitService, User currentUser, Scanner scanner) {
        System.out.println("\n--- Создание привычки ---");
        System.out.print("Введите название привычки: ");
        String title = scanner.nextLine();
        System.out.print("Введите описание привычки: ");
        String description = scanner.nextLine();
        System.out.print("Введите частоту выполнения: 1 - [Каждый день] или 2 - [Каждую неделю]\n");

        Frequency frequency;

        while (true) {
            if (scanner.hasNextInt()) {
                int intInput = scanner.nextInt();
                switch (intInput) {
                    case 1:
                        frequency = Frequency.DAILY;
                        break;
                    case 2:
                        frequency = Frequency.WEEKLY;
                        break;
                    default:
                        System.out.println("Некорректная частота. Пожалуйста, введите 1 для [Каждый день] или 2 для [Каждую неделю]: ");
                        scanner.next(); // Очистка буфера ввода
                        continue;
                }
                break; // Выход из цикла, если ввод корректен
            } else {
                System.out.println("Введите частоту выполнения: 1 - [Каждый день] или 2 - [Каждую неделю]\n");
                scanner.next(); // Очистка буфера ввода от некорректного ввода
            }
        }

        Habit habit = habitService.createHabit(currentUser.getId(), title, description, frequency);
        System.out.println("Привычка создана: " + habit);
    }

    /**
     * Просматривает все привычки текущего пользователя.
     */
    private static void viewHabits(HabitService habitService, User currentUser) {
        System.out.println("\n--- Ваши привычки ---");
        List<Habit> habits = habitService.getHabitsByUser(currentUser.getId());

        if (habits.isEmpty()) {
            System.out.println("У вас нет привычек.");
        } else {
            for (Habit habit : habits) {
                System.out.println(habit);
            }
        }
    }

    /**
     * Обновляет существующую привычку.
     */
    private static void updateHabit(HabitService habitService, User currentUser, Scanner scanner) {
        System.out.println("\n--- Обновление привычки ---");
        viewHabits(habitService, currentUser);
        System.out.print("Введите ID привычки: ");
        String habitId = scanner.nextLine();
        Habit habit = habitService.getHabitById(habitId);

        if (habit == null || !habit.getUserId().equals(currentUser.getId())) {
            System.out.println("Привычка не найдена или вы не являетесь её владельцем.");
            return;
        }

        System.out.print("Введите новое название привычки (оставьте пустым, чтобы оставить без изменений): ");
        String title = scanner.nextLine();

        if (title.isEmpty()) {
            title = habit.getTitle();
        }

        System.out.print("Введите новое описание привычки (оставьте пустым, чтобы оставить без изменений): ");
        String description = scanner.nextLine();

        if (description.isEmpty()) {
            description = habit.getDescription();
        }

        System.out.print("Введите новую частоту выполнения (Введите: 1 - [Каждый день] или 2 - [Каждую неделю], оставьте пустым, чтобы оставить без изменений): ");
        Frequency frequency;

        while (true) {
            if (scanner.hasNextInt()) {
                int intInput = scanner.nextInt();
                switch (intInput) {
                    case 1:
                        frequency = Frequency.DAILY;
                        break;
                    case 2:
                        frequency = Frequency.WEEKLY;
                        break;
                    default:
                        System.out.println("Некорректная частота. Пожалуйста, введите 1 для [Каждый день] или 2 для [Каждую неделю]: ");
                        scanner.next(); // Очистка буфера ввода
                        continue;
                }
                break; // Выход из цикла, если ввод корректен
            } else {
                System.out.println("Введите частоту выполнения: 1 - [Каждый день] или 2 - [Каждую неделю]\n");
                scanner.next(); // Очистка буфера ввода от некорректного ввода
            }
        }

        try {
            Habit updatedHabit = habitService.updateHabit(habitId, title, description, frequency);
            System.out.println("Привычка обновлена: " + updatedHabit);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Удаляет привычку.
     */
    private static void deleteHabit(HabitService habitService, User currentUser, Scanner scanner) {
        System.out.println("\n--- Удаление привычки ---");
        viewHabits(habitService, currentUser);
        System.out.print("Введите ID привычки: ");
        String habitId = scanner.nextLine();
        Habit habit = habitService.getHabitById(habitId);

        if (habit == null || !habit.getUserId().equals(currentUser.getId())) {
            System.out.println("Привычка не найдена или вы не являетесь её владельцем.");
            return;
        }

        System.out.print("Вы уверены, что хотите удалить эту привычку? (да/нет): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("да")) {
            boolean isDeleted = habitService.deleteHabit(habitId);
            if (isDeleted) {
                System.out.println("Привычка удалена.");
            } else {
                System.out.println("Ошибка при удалении привычки.");
            }
        } else {
            System.out.println("Удаление привычки отменено.");
        }
    }

    /**
     * Отмечает привычку как выполненную в указанную дату.
     */
    private static void markHabitAsCompleted(HabitService habitService, User currentUser, Scanner scanner) {
        System.out.println("\n--- Отметка привычки как выполненной ---");
        viewHabits(habitService, currentUser);
        System.out.print("Введите ID привычки: ");
        String habitId = scanner.nextLine();
        Habit habit = habitService.getHabitById(habitId);

        if (habit == null || !habit.getUserId().equals(currentUser.getId())) {
            System.out.println("Привычка не найдена или вы не являетесь её владельцем.");
            return;
        }

        System.out.print("Введите дату выполнения (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateInput);
        } catch (Exception e) {
            System.out.println("Некорректный формат даты.");
            return;
        }

        try {
            habitService.markHabitAsCompleted(habitId, date);
            System.out.println("Привычка '" + habit.getTitle() + "' отмечена как выполненная в " + date + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Просматривает статистику выполнения привычки.
     */
    private static void viewHabitStats(HabitService habitService, User currentUser, Scanner scanner) {
        System.out.println("\n--- Статистика привычки ---");
        viewHabits(habitService, currentUser);
        System.out.print("Введите ID привычки: ");
        String habitId = scanner.nextLine();
        Habit habit = habitService.getHabitById(habitId);

        if (habit == null || !habit.getUserId().equals(currentUser.getId())) {
            System.out.println("Привычка не найдена или вы не являетесь её владельцем.");
            return;
        }

        System.out.print("Введите начальную дату периода (YYYY-MM-DD): ");
        String startInput = scanner.nextLine();
        LocalDate startDate;
        try {
            startDate = LocalDate.parse(startInput);
        } catch (Exception e) {
            System.out.println("Некорректный формат даты.");
            return;
        }

        System.out.print("Введите конечную дату периода (YYYY-MM-DD): ");
        String endInput = scanner.nextLine();
        LocalDate endDate;
        try {
            endDate = LocalDate.parse(endInput);
        } catch (Exception e) {
            System.out.println("Некорректный формат даты.");
            return;
        }

        try {
            double percentage = habitService.getCompletionPercentage(habitId, startDate, endDate);
            int streak = habitService.getCurrentStreak(habitId);
            System.out.printf("Процент выполнения за период %s - %s: %.2f%%\n", startDate, endDate, percentage);
            System.out.printf("Текущая серия выполнения: %d %s\n", streak,
                    habit.getFrequency() == Frequency.DAILY ? "дней" : "недель");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает ввод целочисленного значения.
     */
    private static int getIntInput(Scanner scanner) {
        int input = -1;
        try {
            input = scanner.nextInt();
        } catch (Exception e) {
            // Ошибка будет обработана системой меню
        }
        scanner.nextLine(); // Обрабатывает оставшийся перевод строки
        return input;
    }
}
