package main.java.com.model;

public enum Frequency {
    DAILY(1, "Каждый день"),
    WEEKLY(7, "Каждую неделю");

    private final int daysInterval;
    private final String description;

    // Конструктор для инициализации значений
    Frequency(int daysInterval, String description) {
        this.daysInterval = daysInterval;
        this.description = description;
    }

    /**
     * Возвращает интервал в днях для этой частоты.
     *
     * @return количество дней между повторениями привычки
     */
    public int getDaysInterval() {
        return daysInterval;
    }

    /**
     * Возвращает описание частоты.
     *
     * @return строка с описанием частоты
     */
    public String getDescription() {
        return description;
    }
}
