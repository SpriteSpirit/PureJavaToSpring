package main.java.com.service;

import main.java.com.model.Frequency;
import main.java.com.model.Habit;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс для управления привычками.
 * Содержит методы для создания, чтения, обновления и удаления привычек,
 * а также для отслеживания выполнения и анализа прогресса.
 */
public interface HabitService {
    /**
     * Создает новую привычку.
     *
     * @param userId      Идентификатор пользователя-владельца привычки.
     * @param title       Название привычки.
     * @param description Описание привычки.
     * @param frequency   Частота выполнения привычки (DAILY, WEEKLY).
     * @return Созданная привычка.
     */
    Habit createHabit(String userId, String title, String description, Frequency frequency);

    /**
     * Получает привычку по ее идентификатору.
     *
     * @param habitId Идентификатор привычки.
     * @return Привычка или {@code null}, если не найдена.
     */
    Habit getHabitById(String habitId);


//    /**
//     * Получает привычку по ее идентификатору.
//     *
//     * @param orderNumber номер привычки.
//     * @return Привычка или {@code null}, если не найдена.
//     */
//    Habit getHabitByOrderNumber(int orderNumber);

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
    Habit updateHabit(String habitId, String title, String description, Frequency frequency);

    /**
     * Удаляет привычку по ее идентификатору.
     *
     * @param habitId Идентификатор привычки.
     * @return {@code true}, если удаление прошло успешно, {@code false} иначе.
     */
    boolean deleteHabit(String habitId);

    /**
     * Отмечает привычку как выполненную в указанную дату.
     *
     * @param habitId Идентификатор привычки.
     * @param date    Дата выполнения привычки.
     * @throws IllegalArgumentException если привычка не найдена.
     */
    void markHabitAsCompleted(String habitId, LocalDate date);

    /**
     * Получает текущую серию выполнения привычки.
     *
     * @param habitId Идентификатор привычки.
     * @return Текущая серия.
     * @throws IllegalArgumentException если привычка не найдена.
     */
    int getCurrentStreak(String habitId);

    /**
     * Получает процент выполнения привычки за указанный период.
     *
     * @param habitId   Идентификатор привычки.
     * @param startDate Начальная дата периода.
     * @param endDate   Конечная дата периода.
     * @return Процент выполнения.
     * @throws IllegalArgumentException если привычка не найдена или даты некорректны.
     */
    double getCompletionPercentage(String habitId, LocalDate startDate, LocalDate endDate);

    /**
     * Получает список привычек конкретного пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Список привычек пользователя.
     */
    List<Habit> getHabitsByUser(String userId);

    /**
     * Получает список всех привычек.
     *
     * @return Список всех привычек.
     */
    List<Habit> getHabits();

    /**
     * Получает список дат выполнения для конкретной привычки.
     *
     * @param habitId Идентификатор привычки.
     * @return Список дат выполнения привычки.
     * @throws IllegalArgumentException если привычка не найдена.
     */
    List<LocalDate> getCompletedDatesForHabit(String habitId);
}
