package model;

import interfaces.TaskManager;
import org.junit.jupiter.api.Test;
import services.Managers;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void equals_whenEqualsId_thenEqualsSubtasks() {
        Epic epic = new Epic("Эпик", "Описание");

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", epic);
        subtask1.setId(1);
        Subtask subtask2 = new Subtask("Задача 2", "Описание 2", epic);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи не равны");
    }
}