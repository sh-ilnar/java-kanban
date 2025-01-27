package model;

import interfaces.TaskManager;
import org.junit.jupiter.api.Test;
import services.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void equals_whenEqualsId_thenEqualsSubtasks() {
        Epic epic = new Epic("Эпик", "Описание");

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), epic);
        subtask1.setId(1);
        Subtask subtask2 = new Subtask("Задача 2", "Описание 2", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), epic);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи не равны");
    }
}