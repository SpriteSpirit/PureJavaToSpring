package main.java.com.service;

import main.java.com.model.Habit;

import java.time.LocalDate;
import java.util.List;


/**
 * Интерфейс для управления привычками.
 * - `createHabit`: создать привычку.
 * - `getHabitsByUser`: получить привычки пользователя.
 * - `getHabitById`: получить привычку по идентификатору.
 * - `updateHabit`: обновить привычку.
 * - `deleteHabit`: удалить привычку.
 * - `markHabitAsCompleted`: отметить привычку выполненной.
 * - `getCompletedDatesForHabit`: получить даты выполнения привычки.
 * - `getCurrentStreak`: получить текущую серию выполнения.
 * - `getCompletionPercentage`: получить процент выполнения за период.
 */
public interface HabitService {
    Habit createHabit(String userId, String name, String description, String frequency);
    List<Habit> getHabitsByUser(String userId);
    Habit getHabitById(String habitId);
    Habit updateHabit(String habitId, String name, String description, String frequency);
     boolean deleteHabit(String habitId);
    void markHabitAsCompleted(String habitId, LocalDate date);
    List<LocalDate> getCompletedDatesForHabit(String habitId);
    int getCurrentStreak(String habitId);
    double getCompletionPercentage(String habitId, LocalDate startDate, LocalDate endDate);
}
