package main.java.com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Класс для описания привычки.
 * Содержит поля: id, title, description, frequency, createdDate
 */
public class Habit {
    private final String id;
    private String title;
    private String description;
    private Frequency frequency;
    private final List<LocalDate> completionDates;

    /**
     * Конструктор класса Habit.
     * Инициализирует поля класса
     *
     * @param title       название привычки
     * @param description описание привычки
     * @param frequency   частота выполнения
     */
    public Habit(String title, String description, Frequency frequency) {
        this.id = UUID.randomUUID().toString();
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
        completionDates.add(date);
    }

    // Методы доступа (геттеры и сеттеры)
    /**
     * Возвращает уникальный идентификатор привычки.
     *
     * @return Строка, представляющая ID привычки
     */
    public String getId() {
        return id;
    }

    /**
     * Получает название привычки.
     *
     * @return Название привычки
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает новое название для привычки.
     *
     * @param title Новое название привычки
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получает описание привычки.
     *
     * @return Описание привычки
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает новое описание для привычки.
     *
     * @param description Новое описание привычки
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Получает частоту выполнения привычки.
     *
     * @return Объект Frequency, представляющий частоту выполнения
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     * Устанавливает новую частоту выполнения для привычки.
     *
     * @param frequency Новая частота выполнения
     */
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    /**
     * Возвращает список дат, когда привычка была выполнена.
     *
     * @return Список дат выполнения привычки
     */
    public List<LocalDate> getCompletionDates() {
        return completionDates;
    }
}
