package com.habitapp.service;

import com.habitapp.model.Frequency;
import com.habitapp.model.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HabitServiceTest {

    private HabitService habitService;

    @BeforeEach
    void setUp() {
        habitService = new InMemoryHabitService();
    }

    @Test
    void testCreateHabit() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Бегать", "Ежедневный бег", Frequency.DAILY);
        assertNotNull(habit);
        assertEquals("Бегать", habit.getTitle());
        assertEquals("Ежедневный бег", habit.getDescription());
        assertEquals(Frequency.DAILY, habit.getFrequency());
        assertEquals(userId, habit.getUserId());
    }

    @Test
    void testGetHabitById() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Читать", "Читать книгу", Frequency.DAILY);
        String habitId = habit.getId();

        Habit fetchedHabit = habitService.getHabitById(habitId);
        assertNotNull(fetchedHabit);
        assertEquals(habitId, fetchedHabit.getId());
        assertEquals("Читать", fetchedHabit.getTitle());
    }

    @Test
    void testUpdateHabit() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Медитировать", "Утренняя медитация", Frequency.DAILY);
        String habitId = habit.getId();

        Habit updatedHabit = habitService.updateHabit(habitId, "Медитировать", "Вечерняя медитация", Frequency.DAILY);
        assertEquals("Вечерняя медитация", updatedHabit.getDescription());
    }

    @Test
    void testDeleteHabit() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Пить воду", "8 стаканов в день", Frequency.DAILY);
        String habitId = habit.getId();

        boolean isDeleted = habitService.deleteHabit(habitId);
        assertTrue(isDeleted);
        assertNull(habitService.getHabitById(habitId));
    }

    @Test
    void testMarkHabitAsCompleted() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Писать код", "Писать много хорошего кода каждый день", Frequency.DAILY);
        String habitId = habit.getId();

        LocalDate today = LocalDate.now();
        habitService.markHabitAsCompleted(habitId, today);
        List<LocalDate> completedDates = habitService.getCompletedDatesForHabit(habitId);
        assertTrue(completedDates.contains(today));
    }

    @Test
    void testGetCurrentStreak() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Смотреть лекции", "Смотреть лекции по Java", Frequency.DAILY);
        String habitId = habit.getId();

        LocalDate today = LocalDate.now();
        habitService.markHabitAsCompleted(habitId, today.minusDays(1));
        habitService.markHabitAsCompleted(habitId, today);
        int currentStreak = habitService.getCurrentStreak(habitId);
        assertEquals(2, currentStreak);
    }

    @Test
    void testGetCompletionPercentage() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Пробежка", "Пробежать 5 км за булочками до магазина", Frequency.DAILY);
        String habitId = habit.getId();

        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        habitService.markHabitAsCompleted(habitId, endDate.minusDays(3));
        habitService.markHabitAsCompleted(habitId, endDate.minusDays(2));
        double completionPercentage = habitService.getCompletionPercentage(habitId, startDate, endDate);
        assertEquals(25.0, completionPercentage, 0.01);
    }

    @Test
    void testGetHabitsByUser() {
        String userId = UUID.randomUUID().toString();
        habitService.createHabit(userId, "Готовить ужин", "Приготовить что-нибудь поесть", Frequency.DAILY);
        habitService.createHabit(userId, "Поиграть с котом", "Развлечь кота", Frequency.DAILY);
        List<Habit> userHabits = habitService.getHabitsByUser(userId);
        assertEquals(2, userHabits.size());
    }

    @Test
    void testGetHabits() {
        String userId1 = UUID.randomUUID().toString();
        String userId2 = UUID.randomUUID().toString();
        habitService.createHabit(userId1, "Рисовать", "Рисовать каракули", Frequency.DAILY);
        habitService.createHabit(userId2, "Потягиваться", "Потянуться и не поймать судорогу", Frequency.DAILY);
        List<Habit> allHabits = habitService.getHabits();
        assertEquals(2, allHabits.size());
    }

    @Test
    void testGetCompletedDatesForHabit() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Спать", "Спать 8 часов", Frequency.DAILY);
        String habitId = habit.getId();

        LocalDate date1 = LocalDate.now().minusDays(1);
        LocalDate date2 = LocalDate.now();
        habitService.markHabitAsCompleted(habitId, date1);
        habitService.markHabitAsCompleted(habitId, date2);
        List<LocalDate> completedDates = habitService.getCompletedDatesForHabit(habitId);
        assertTrue(completedDates.contains(date1));
        assertTrue(completedDates.contains(date2));
    }
}