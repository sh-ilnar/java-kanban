package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    TaskManager createTaskManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        return Managers.getDefault(historyManager);
    }

    @Test
    void createTask_newTask_addInTaskList() {
        Task newTask = new Task("Тест", "Тестовое описание");

        taskManager.createTask(newTask);
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals(newTask, taskList.getFirst(), "Задачи не совпадают");
    }

    @Test
    void createEpic_newEpic_addInEpicList() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");

        taskManager.createEpic(newEpic);
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals(newEpic, epicList.getFirst(), "Эпики не совпадают");
    }

    @Test
    void createSubtask_newSubtask_addInSubtaskList() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);

        taskManager.createSubtask(newSubtask);
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals(newSubtask, subtaskList.getFirst(), "Подзадачи не совпадают");
    }

    @Test
    void createTask_intersectionTask_catchException() {
        Task task1 = new Task("Тест", "Тестовое описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.05.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        taskManager.createTask(task1);

        assertThrows(IllegalArgumentException.class, () -> {
            Task task2 = new Task("Тест", "Тестовое описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.05.2025 01:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            taskManager.createTask(task2);
        }, "Пересечение по времени должно приводить к ошибке");
    }
}