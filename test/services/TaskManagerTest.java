package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class TaskManagerTest<T extends TaskManager> {

    TaskManager taskManager;

    abstract TaskManager createTaskManager();

    @BeforeEach
    void setTaskManager() {
        taskManager = createTaskManager();
    }

    @Test
    void getTaskById_validId_ReturnTask() {
        Task task1 = new Task("Тест", "Тестовое описание");
        taskManager.createTask(task1);

        int taskId = task1.getId();

        Assertions.assertEquals(task1, taskManager.getTaskById(taskId), "Задачи не совпадают");
    }

    @Test
    void getEpicById_validId_ReturnEpic() {
        Epic epic1 = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(epic1);

        int epicId = epic1.getId();

        Assertions.assertEquals(epic1, taskManager.getEpicById(epicId), "Эпики не совпадают");
    }

    @Test
    void getSubtaskById_validId_ReturnSubtask() {
        Epic epic1 = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(epic1);
        Subtask subtask1_1 = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("20.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), epic1);

        taskManager.createSubtask(subtask1_1);
        int subtaskId = subtask1_1.getId();

        Assertions.assertEquals(subtask1_1, taskManager.getSubtaskById(subtaskId), "Подзадачи не совпадают");
    }

    @Test
    void createTask_newTask_taskIsNotChangedAfterGet() {
        Task newTask = new Task("Тест", "Тестовое описание");
        taskManager.createTask(newTask);
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals("Тест", newTask.getName(), "Наименование задачи было изменено");
        Assertions.assertEquals("Тестовое описание", newTask.getDescription(), "Описание задачи было изменено");
        Assertions.assertEquals(Status.NEW, newTask.getStatus(), "Статус задачи был изменен");
    }

    @Test
    void createEpic_newEpic_epicIsNotChangedAfterGet() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals("Тест", newEpic.getName(), "Наименование эпика было изменено");
        Assertions.assertEquals("Тестовое описание", newEpic.getDescription(), "Описание эпика было изменено");
    }

    @Test
    void createSubtask_newSubtask_subtaskIsNotChangedAfterGet() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.02.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask);
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals("Подзадача 1 эпика 1", newSubtask.getName(), "Наименование подзадачи было изменено");
        Assertions.assertEquals("Описание", newSubtask.getDescription(), "Описание подзадачи было изменено");
        Assertions.assertEquals(Status.NEW, newSubtask.getStatus(), "Статус подзадачи был изменен");
    }

    @Test
    void deleteAllTasks_newTask_emptyTaskList() {
        Task newTask = new Task("Тест", "Тестовое описание");
        taskManager.createTask(newTask);

        taskManager.deleteAllTasks();
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals(0, taskList.size(), "Список задач не пустой");
    }

    @Test
    void deleteAllEpics_newEpic_emptyTaskList() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);

        taskManager.deleteAllEpics();
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals(0, epicList.size(), "Список эпиков' не пустой");
    }

    @Test
    void deleteAllSubtasks_newSubtask_emptySubtaskList() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.02.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask);

        taskManager.deleteAllSubtasks();
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals(0, subtaskList.size(), "Список подзадач не пустой");
    }

    @Test
    void deleteTaskById_newTask_emptyTaskList() {
        taskManager.deleteAllTasks();
        Task newTask = new Task("Тест", "Тестовое описание");
        taskManager.createTask(newTask);

        taskManager.deleteTaskById(newTask.getId());
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals(0, taskList.size(), "Задача не удалена");
    }

    @Test
    void deleteEpicById_newEpic_emptyTaskList() {
        taskManager.deleteAllEpics();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);

        taskManager.deleteEpicById(newEpic.getId());
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals(0, epicList.size(), "Эпик не удален");
    }

    @Test
    void deleteSubtaskById_newSubtask_emptySubtaskList() {
        taskManager.deleteAllEpics();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask);

        taskManager.deleteSubtaskById(newSubtask.getId());
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals(0, subtaskList.size(), "Подзадача не удалена");
    }

    @Test
    void updateTask_newTask_updatedTask() {
        Task newTask = new Task("Тест", "Тестовое описание");
        taskManager.createTask(newTask);
        int newTaskId = newTask.getId();
        Task newUpdateTask = new Task("Новое наименование", "Новое описание");
        newUpdateTask.setId(newTaskId);

        taskManager.updateTask(newUpdateTask);

        Assertions.assertEquals(newUpdateTask, taskManager.getTaskById(newTaskId));
    }

    @Test
    void updateEpic_newEpic_updatedEpic() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        int newEpicId = newEpic.getId();
        Epic newUpdateEpic = new Epic("Новое наименование", "Новое описание");
        newUpdateEpic.setId(newEpicId);

        taskManager.updateEpic(newUpdateEpic);

        Assertions.assertEquals(newUpdateEpic, taskManager.getEpicById(newEpicId));
    }

    @Test
    void updateSubtask_newSubtask_updatedSubtask() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask);
        int newSubtaskId = newSubtask.getId();
        Subtask newUpdateSubtask = new Subtask("Новое наименование", "Новое описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("02.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        newUpdateSubtask.setId(newSubtaskId);

        taskManager.updateSubtask(newUpdateSubtask);

        Assertions.assertEquals(newUpdateSubtask, taskManager.getSubtaskById(newSubtaskId));
    }

    @Test
    void NewEpicStatus_emptySubtask_returnNewStatus() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);

        Assertions.assertEquals(Status.NEW, newEpic.getStatus(), "Некорректный статус у нового эпика");
    }

    @Test
    void NewEpicStatus_newStatusSubtask_returnNewStatus() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask1 = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask1);
        Subtask newSubtask2 = new Subtask("Подзадача 2 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("02.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask2);

        Assertions.assertEquals(Status.NEW, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void NewEpicStatus_newAndDoneStatusSubtask_returnNewStatus() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask1 = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.03.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask1);
        Subtask newSubtask2 = new Subtask("Подзадача 2 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("02.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask2);
        newSubtask1.setStatus(Status.DONE);

        Assertions.assertEquals(Status.NEW, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_inProgressStatusSubtask_returnInProgressStatus() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask);
        newSubtask.setStatus(Status.IN_PROGRESS);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_doneStatusSubtask_returnInProgressStatus() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask1 = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask1);
        Subtask newSubtask2 = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("02.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask2);
        newSubtask1.setStatus(Status.DONE);
        newSubtask2.setStatus(Status.DONE);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.DONE, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_newDoneStatusSubtask_returnInProgressStatus() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask);
        Subtask newSubtask2 = new Subtask("Подзадача 2 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2024 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask2);
        newSubtask2.setStatus(Status.DONE);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_mixStatusSubtask_returnInProgressStatus() {
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask);
        Subtask newSubtask2 = new Subtask("Подзадача 2 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2024 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask2);
        newSubtask2.setStatus(Status.DONE);
        Subtask newSubtask3 = new Subtask("Подзадача 3 эпика 1", "Описание", Duration.of(240, ChronoUnit.MINUTES), LocalDateTime.parse("10.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), newEpic);
        taskManager.createSubtask(newSubtask3);
        newSubtask3.setStatus(Status.IN_PROGRESS);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, newEpic.getStatus(), "Некорректный статус у эпика");
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
