package com.habitapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Класс для описания привычки.
 * Содержит поля: id, userId, title, description, frequency, completionDates
 */
public class Habit {
    private final String id;
//    private final int orderNumber;
//    private static int nextOrderNumber = 1;
    private final String userId; // Идентификатор пользователя-владельца
    private String title;
    private String description;
    private Frequency frequency;
    private final List<LocalDate> completionDates;

    /**
     * Конструктор класса Habit.
     *
     * @param userId      идентификатор пользователя-владельца привычки
     * @param title       название привычки
     * @param description описание привычки
     * @param frequency   частота выполнения
     */
    public Habit(String userId, String title, String description, Frequency frequency) {
        this.id = UUID.randomUUID().toString();
//        this.orderNumber = nextOrderNumber++;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.completionDates = new ArrayList<>();
    }

    /**
     * Отмечает привычку как выполненную в указанную дату.
     *
     * @param date дата выполнения привычки
     */
    public void markCompleted(LocalDate date) {
        if (!completionDates.contains(date)) {
            completionDates.add(date);
        }
    }

    // Геттеры и сеттеры
//    public int getOrderNumber() {
//        return orderNumber;
//    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    /**
     * Возвращает копию списка дат выполнения привычки.
     *
     * @return Список дат выполнения
     */
    public List<LocalDate> getCompletionDates() {
        return Collections.unmodifiableList(completionDates);
    }

    @Override
    public String toString() {
        return "Привычка{" +
                "ID: " + getId() +
                ", Название: " + getTitle() +
                ", Описание: " + getDescription() +
                ", Частота: " + getFrequency().getDescription() +
                ", Даты завершения: " + getCompletionDates() +
                '}';
    }
}
