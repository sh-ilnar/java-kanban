package model;

import interfaces.TaskManager;
import org.junit.jupiter.api.Test;
import services.Managers;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void equals_whenEqualsId_thenEqualsTasks() {
        Task task1 = new Task("Задача 1", "Описание 1");
        task1.setId(1);
        Task task2 = new Task("Задача 2", "Описание 2");
        task2.setId(1);

        assertEquals(task1, task2, "Задачи не равны");
    }
}