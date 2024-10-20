package com.habitapp.service;

import com.habitapp.model.Frequency;
import com.habitapp.model.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHabitServiceTest {

    private HabitService habitService;

    @BeforeEach
    void setUp() {
        habitService = new InMemoryHabitService();
    }

    @Test
    void testCreateHabit() {
        Habit habit = habitService.createHabit("Мария", "Бегать", "Бегать за булками", Frequency.DAILY);

        assertNotNull(habit);
        assertEquals("Бегать", habit.getTitle());
        assertEquals("Бегать за булками", habit.getDescription());
        assertEquals(Frequency.DAILY, habit.getFrequency());
    }

    @Test
    void testGetHabitById() {
        Habit habit = habitService.createHabit("Федор", "Пить воду", "8 стаканов в день", Frequency.DAILY);
        Habit retrievedHabit = habitService.getHabitById(habit.getId());

        assertNotNull(retrievedHabit);
        assertEquals("Пить воду", retrievedHabit.getTitle());
    }

    @Test
    void testUpdateHabit() {
        Habit habit = habitService.createHabit("Дарья", "Читать", "Читать книгу", Frequency.DAILY);
        Habit updatedHabit = habitService.updateHabit(habit.getId(), "Перечитать", "Перечитать книгу", Frequency.WEEKLY);

        assertEquals("Перечитать", updatedHabit.getTitle());
        assertEquals("Перечитать книгу", updatedHabit.getDescription());
        assertEquals(Frequency.WEEKLY, updatedHabit.getFrequency());
    }

    @Test
    void testUpdateNonExistingHabit() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitService.updateHabit("несуществующий_id_в_виде_строки", "Перечитать", "Перечитать все книги", Frequency.WEEKLY);
        });
    }

    @Test
    void testDeleteHabit() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Готовить", "Готовить ужин", Frequency.DAILY);

        boolean deleted = habitService.deleteHabit(habit.getId());

        assertTrue(deleted);
        assertNull(habitService.getHabitById(habit.getId()));
    }

    @Test
    void testDeleteNonExistingHabit() {
        boolean deleted = habitService.deleteHabit("несуществующий_id_в_виде_строки");

        assertFalse(deleted);
    }

    @Test
    void testMarkHabitAsCompleted() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Поднять", "Поднять всё, что уронили коты", Frequency.DAILY);

        LocalDate date = LocalDate.now();
        habitService.markHabitAsCompleted(habit.getId(), date);
        List<LocalDate> completedDates = habit.getCompletionDates();

        assertTrue(completedDates.contains(date));
    }

    @Test
    void testMarkNonExistingHabitAsCompleted() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitService.markHabitAsCompleted("несуществующий_id_в_виде_строки", LocalDate.now());
        });
    }

    @Test
    void testGetCurrentStreak() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Обед", "Съесть что-нибудь", Frequency.DAILY);

        LocalDate today = LocalDate.now();
        habitService.markHabitAsCompleted(habit.getId(), today);
        habitService.markHabitAsCompleted(habit.getId(), today.minusDays(1));

        int streak = habitService.getCurrentStreak(habit.getId());

        assertEquals(2, streak);
    }

    @Test
    void testGetCurrentStreakForNonExistingHabit() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitService.getCurrentStreak("несуществующий_id_в_виде_строки");
        });
    }

    @Test
    void testGetCompletionPercentage() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Писать код", "Писать хороший код", Frequency.DAILY);

        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        habitService.markHabitAsCompleted(habit.getId(), endDate.minusDays(3));
        habitService.markHabitAsCompleted(habit.getId(), endDate.minusDays(2));

        double percentage = habitService.getCompletionPercentage(habit.getId(), startDate, endDate);

        assertEquals(25.0, percentage, 0.01);
    }

    @Test
    void testGetCompletionPercentageForNonExistingHabit() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () -> {
            habitService.getCompletionPercentage("несуществующий_id_в_виде_строки", startDate, endDate);
        });
    }

    @Test
    void testGetHabitsByUser() {
        habitService.createHabit("user1", "Habit 1", "Description 1", Frequency.DAILY);
        habitService.createHabit("user1", "Habit 2", "Description 2", Frequency.WEEKLY);

        List<Habit> habits = habitService.getHabitsByUser("user1");

        assertEquals(2, habits.size());
    }

    @Test
    void testGetHabits() {
        String userId = UUID.randomUUID().toString();

        habitService.createHabit(userId, "Медитация", "Медитировать", Frequency.DAILY);
        habitService.createHabit(userId, "Йога", "Йогировать", Frequency.WEEKLY);

        List<Habit> habits = habitService.getHabits();

        assertEquals(2, habits.size());
    }

    @Test
    void testGetCompletedDatesForHabit() {
        String userId = UUID.randomUUID().toString();
        Habit habit = habitService.createHabit(userId, "Танцевать", "Танцевать 10 минут", Frequency.DAILY);

        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().minusDays(1);

        habitService.markHabitAsCompleted(habit.getId(), date1);
        habitService.markHabitAsCompleted(habit.getId(), date2);

        List<LocalDate> completedDates = habitService.getCompletedDatesForHabit(habit.getId());

        assertEquals(2, completedDates.size());
        assertTrue(completedDates.contains(date1));
        assertTrue(completedDates.contains(date2));
    }

    @Test
    void testGetCompletedDatesForNonExistingHabit() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitService.getCompletedDatesForHabit("несуществующий_id_в_виде_строки");
        });
    }
}
