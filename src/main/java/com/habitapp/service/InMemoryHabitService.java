package com.habitapp.service;

import com.habitapp.model.Frequency;
import com.habitapp.model.Habit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Реализация HabitService для управления привычками в памяти.
 * - `createHabit`: создание новой привычки.
 * - `updateHabit`: редактирование привычки.
 * - `deleteHabit`: удаление привычки.
 * - `getHabits`: получение списка всех привычек.
 * - `markHabitAsCompleted`: отметка о выполнении привычки.
 * - `getCurrentStreak`: получение текущей серии выполнения.
 * - `getCompletionPercentage`: получение процента выполнения за период.
 * - `getHabitsByUser`: получение привычек конкретного пользователя.
 * - `getCompletedDatesForHabit`: получение дат выполнения привычки.
 */
public class InMemoryHabitService implements HabitService {
    private final Map<String, Habit> habits = new HashMap<>();
//    private final Map<Integer, Habit> habitsNyNumber = new HashMap<>();
    private final Map<String, List<Habit>> userHabits = new HashMap<>();

    /**
     * Создает новую привычку.
     *
     * @param userId      Идентификатор пользователя-владельца привычки.
     * @param title       Название привычки.
     * @param description Описание привычки.
     * @param frequency   Частота выполнения привычки.
     * @return Созданная привычка.
     */
    @Override
    public Habit createHabit(String userId, String title, String description, Frequency frequency) {
        Habit habit = new Habit(userId, title, description, frequency);
        habits.put(habit.getId(), habit);
//        habitsNyNumber.put(habit.getOrderNumber(), habit);
        userHabits.computeIfAbsent(userId, k -> new ArrayList<>()).add(habit);
        return habit;
    }

    /**
     * Получает привычку по ее идентификатору.
     *
     * @param habitId Идентификатор привычки.
     * @return Привычка или {@code null}, если не найдена.
     */
    @Override
    public Habit getHabitById(String habitId) {
        return habits.get(habitId);
    }

//    /**
//     * Получает привычку по ее номеру.
//     *
//     * @param orderNumber номер привычки.
//     * @return Привычка или {@code null}, если не найдена.
//     */
//    @Override
//    public Habit getHabitByOrderNumber(int orderNumber) {
//        return habitsNyNumber.get(orderNumber);
//    }

    /**
     * Обновляет существующую привычку.
     *
     * @param habitId     Идентификатор привычки.
     * @param title       Новое название привычки.
     * @param description Новое описание привычки.
     * @param frequency   Новая частота выполнения привычки.
     * @return Обновленная привычка.
     * @throws IllegalArgumentException если привычка не найдена.
     */
    @Override
    public Habit updateHabit(String habitId, String title, String description, Frequency frequency) {
        Habit habit = habits.get(habitId);
        if (habit == null) {
            throw new IllegalArgumentException("Привычка не найдена");
        }
        habit.setTitle(title);
        habit.setDescription(description);
        habit.setFrequency(frequency);
        return habit;
    }

    /**
     * Удаляет привычку по ее идентификатору.
     *
     * @param habitId Идентификатор привычки.
     * @return {@code true}, если удаление прошло успешно, {@code false} иначе.
     */
    @Override
    public boolean deleteHabit(String habitId) {
        Habit removed = habits.remove(habitId);
        if (removed != null) {
            List<Habit> userHabitList = userHabits.get(removed.getUserId());
            if (userHabitList != null) {
                userHabitList.remove(removed);
                if (userHabitList.isEmpty()) {
                    userHabits.remove(removed.getUserId());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Отмечает выполнение привычки в указанный день.
     *
     * @param habitId Идентификатор привычки.
     * @param date    Дата выполнения.
     * @throws IllegalArgumentException если привычка не найдена.
     */
    @Override
    public void markHabitAsCompleted(String habitId, LocalDate date) {
        Habit habit = habits.get(habitId);
        if (habit == null) {
            throw new IllegalArgumentException("Привычка не найдена");
        }
        habit.markCompleted(date);
    }

    /**
     * Возвращает текущую серию выполнения привычки.
     *
     * @param habitId Идентификатор привычки.
     * @return Текущая серия.
     * @throws IllegalArgumentException если привычка не найдена.
     */
    @Override
    public int getCurrentStreak(String habitId) {
        Habit habit = habits.get(habitId);
        if (habit == null) {
            throw new IllegalArgumentException("Привычка не найдена");
        }

        List<LocalDate> dates = new ArrayList<>(habit.getCompletionDates());
        if (dates.isEmpty()) {
            return 0;
        }

        Collections.sort(dates);
        int streak = 0;
        LocalDate today = LocalDate.now();

        for (int i = dates.size() - 1; i >= 0; i--) {
            LocalDate date = dates.get(i);
            if (habit.getFrequency() == Frequency.DAILY) {
                long daysBetween = ChronoUnit.DAYS.between(date, today) - streak;
                if (daysBetween == 0 || daysBetween == 1) {
                    streak++;
                } else {
                    break;
                }
            } else if (habit.getFrequency() == Frequency.WEEKLY) {
                long weeksBetween = ChronoUnit.WEEKS.between(date, today) - streak;
                if (weeksBetween == 0 || weeksBetween == 1) {
                    streak++;
                } else {
                    break;
                }
            }
        }

        return streak;
    }

    /**
     * Возвращает процент выполнения привычки за указанный период.
     *
     * @param habitId   Идентификатор привычки.
     * @param startDate Начальная дата периода.
     * @param endDate   Конечная дата периода.
     * @return Процент выполнения.
     * @throws IllegalArgumentException если привычка не найдена или даты некорректны.
     */
    @Override
    public double getCompletionPercentage(String habitId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Начальная дата должна быть до конечной даты");
        }

        Habit habit = habits.get(habitId);
        if (habit == null) {
            throw new IllegalArgumentException("Привычка не найдена");
        }

        List<LocalDate> dates = habit.getCompletionDates();
        if (dates.isEmpty()) {
            return 0.0;
        }

        // Фильтруем даты в указанном диапазоне
        List<LocalDate> filteredDates = new ArrayList<>();
        for (LocalDate date : dates) {
            if ((date.isEqual(startDate) || date.isAfter(startDate)) &&
                    (date.isEqual(endDate) || date.isBefore(endDate))) {
                filteredDates.add(date);
            }
        }

        long totalUnits;
        long completedUnits = 0;

        if (habit.getFrequency() == Frequency.DAILY) {
            totalUnits = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            completedUnits = filteredDates.size();
        } else { // WEEKLY
            totalUnits = ChronoUnit.WEEKS.between(startDate, endDate) + 1;
            // Для еженедельных привычек считаем количество недель с завершением
            Set<Long> completedWeeks = new HashSet<>();
            for (LocalDate date : filteredDates) {
                completedWeeks.add(ChronoUnit.WEEKS.between(startDate, date));
            }
            completedUnits = completedWeeks.size();
        }

        if (totalUnits == 0) {
            return 0.0;
        }

        return ((double) completedUnits / totalUnits) * 100;
    }

    /**
     * Получает список привычек конкретного пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Список привычек пользователя.
     */
    @Override
    public List<Habit> getHabitsByUser(String userId) {
        List<Habit> habitsList = userHabits.get(userId);
        if (habitsList == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(habitsList); // Возвращаем копию списка для безопасности
    }

    /**
     * Получает список всех привычек.
     *
     * @return Список всех привычек.
     */
    @Override
    public List<Habit> getHabits() {
        return new ArrayList<>(habits.values());
    }

    /**
     * Получает список дат выполнения для конкретной привычки.
     *
     * @param habitId Идентификатор привычки.
     * @return Список дат выполнения привычки.
     * @throws IllegalArgumentException если привычка не найдена.
     */
    @Override
    public List<LocalDate> getCompletedDatesForHabit(String habitId) {
        Habit habit = habits.get(habitId);
        if (habit == null) {
            throw new IllegalArgumentException("Привычка не найдена");
        }
        return habit.getCompletionDates();
    }
}