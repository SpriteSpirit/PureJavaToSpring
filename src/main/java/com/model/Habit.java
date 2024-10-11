package main.java.com.model;

import java.time.LocalDate;


/**
 * Класс для описания привычки.
 * Содержит поля: id, title, description, frequency, createdDate
 */
public class Habit {
    private final int id;
    private String title;
    private String description;
    private String frequency;
    private final LocalDate createdDate;

    /**
     * Конструктор класса Habit.
     * Инициализирует поля класса
     *
     * @param id порядковый номер привычки
     * @param title название привычки
     * @param description описание привычки
     * @param frequency частота выполнения
     * @param createdDate дата создания
     */
    public Habit(int id, String title, String description, String frequency, LocalDate createdDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.createdDate = createdDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
