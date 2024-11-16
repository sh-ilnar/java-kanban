package services;

import interfaces.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void getTaskById_validId_ReturnTask() {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Тест", "Тестовое описание");
        taskManager.createTask(task1);

        int taskId = task1.getId();

        Assertions.assertEquals(task1, taskManager.getTaskById(taskId), "Задачи не совпадают");
    }

    @Test
    void getEpicById_validId_ReturnEpic() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(epic1);

        int epicId = epic1.getId();

        Assertions.assertEquals(epic1, taskManager.getEpicById(epicId), "Эпики не совпадают");
    }

    @Test
    void getSubtaskById_validId_ReturnSubtask() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(epic1);
        Subtask subtask1_1 = new Subtask("Подзадача 1 эпика 1", "Описание", epic1);

        taskManager.createSubtask(subtask1_1);
        int subtaskId = subtask1_1.getId();

        Assertions.assertEquals(subtask1_1, taskManager.getSubtaskById(subtaskId), "Подзадачи не совпадают");
    }

    @Test
    void createTask_newTask_addInTaskList() {
        TaskManager taskManager = Managers.getDefault();
        Task newTask = new Task("Тест", "Тестовое описание");

        taskManager.createTask(newTask);
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals(newTask, taskList.getFirst(), "Задачи не совпадают");
    }

    @Test
    void createTask_newTask_taskIsNotChanged() {
        TaskManager taskManager = Managers.getDefault();
        Task newTask = new Task("Тест", "Тестовое описание");
        taskManager.createTask(newTask);
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals("Тест", newTask.getName(), "Наименование задачи было изменено");
        Assertions.assertEquals("Тестовое описание", newTask.getDescription(), "Описание задачи было изменено");
        Assertions.assertEquals(Status.NEW, newTask.getStatus(), "Статус задачи был изменен");
    }

    @Test
    void createEpic_newEpic_addInEpicList() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");

        taskManager.createEpic(newEpic);
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals(newEpic, epicList.getFirst(), "Эпики не совпадают");
    }

    @Test
    void createEpic_newEpic_epicIsNotChanged() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals("Тест", newEpic.getName(), "Наименование эпика было изменено");
        Assertions.assertEquals("Тестовое описание", newEpic.getDescription(), "Описание эпика было изменено");
    }

    @Test
    void createSubtask_newSubtask_addInSubtaskList() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);

        taskManager.createSubtask(newSubtask);
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals(newSubtask, subtaskList.getFirst(), "Подзадачи не совпадают");
    }

    @Test
    void createSubtask_newSubtask_subtaskIsNotChanged() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals("Подзадача 1 эпика 1", newSubtask.getName(), "Наименование подзадачи было изменено");
        Assertions.assertEquals("Описание", newSubtask.getDescription(), "Описание подзадачи было изменено");
        Assertions.assertEquals(Status.NEW, newSubtask.getStatus(), "Статус подзадачи был изменен");
    }

    @Test
    void deleteAllTasks_newTask_emptyTaskList() {
        TaskManager taskManager = Managers.getDefault();
        Task newTask = new Task("Тест", "Тестовое описание");
        taskManager.createTask(newTask);

        taskManager.deleteAllTasks();
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals(0, taskList.size(), "Список задач не пустой");
    }

    @Test
    void deleteAllEpics_newEpic_emptyTaskList() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);

        taskManager.deleteAllEpics();
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals(0, epicList.size(), "Список эпиков' не пустой");
    }

    @Test
    void deleteAllSubtasks_newSubtask_emptySubtaskList() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);

        taskManager.deleteAllSubtasks();
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals(0, subtaskList.size(), "Список подзадач не пустой");
    }

    @Test
    void deleteTaskById_newTask_emptyTaskList() {
        TaskManager taskManager = Managers.getDefault();
        Task newTask = new Task("Тест", "Тестовое описание");
        taskManager.createTask(newTask);

        taskManager.deleteTaskById(newTask.getId());
        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals(0, taskList.size(), "Задача не удалена");
    }

    @Test
    void deleteEpicById_newEpic_emptyTaskList() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);

        taskManager.deleteEpicById(newEpic.getId());
        List<Epic> epicList = taskManager.getEpics();

        Assertions.assertEquals(0, epicList.size(), "Эпик не удален");
    }

    @Test
    void deleteSubtaskById_newSubtask_emptySubtaskList() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);

        taskManager.deleteSubtaskById(newSubtask.getId());
        List<Subtask> subtaskList = taskManager.getSubtasksByEpic(newEpic);

        Assertions.assertEquals(0, subtaskList.size(), "Подзадача не удалена");
    }

    @Test
    void updateTask_newTask_updatedTask() {
        TaskManager taskManager = Managers.getDefault();
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
        TaskManager taskManager = Managers.getDefault();
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
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);
        int newSubtaskId = newSubtask.getId();
        Subtask newUpdateSubtask = new Subtask("Новое наименование", "Новое описание", newEpic);
        newUpdateSubtask.setId(newSubtaskId);

        taskManager.updateSubtask(newUpdateSubtask);

        Assertions.assertEquals(newUpdateSubtask, taskManager.getSubtaskById(newSubtaskId));
    }

    @Test
    void NewEpicStatus_emptySubtask_returnNewStatus() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);

        Assertions.assertEquals(Status.NEW, newEpic.getStatus(), "Некорректный статус у нового эпика");
    }

    @Test
    void NewEpicStatus_newStatusSubtask_returnNewStatus() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);

        Assertions.assertEquals(Status.NEW, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_inProgressStatusSubtask_returnInProgressStatus() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);
        newSubtask.setStatus(Status.IN_PROGRESS);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_doneStatusSubtask_returnInProgressStatus() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);
        newSubtask.setStatus(Status.DONE);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.DONE, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_newDoneStatusSubtask_returnInProgressStatus() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);
        Subtask newSubtask2 = new Subtask("Подзадача 2 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask2);
        newSubtask2.setStatus(Status.DONE);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, newEpic.getStatus(), "Некорректный статус у эпика");
    }

    @Test
    void InProgressEpicStatus_mixStatusSubtask_returnInProgressStatus() {
        TaskManager taskManager = Managers.getDefault();
        Epic newEpic = new Epic("Тест", "Тестовое описание");
        taskManager.createEpic(newEpic);
        Subtask newSubtask = new Subtask("Подзадача 1 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask);
        Subtask newSubtask2 = new Subtask("Подзадача 2 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask2);
        newSubtask2.setStatus(Status.DONE);
        Subtask newSubtask3 = new Subtask("Подзадача 3 эпика 1", "Описание", newEpic);
        taskManager.createSubtask(newSubtask3);
        newSubtask3.setStatus(Status.IN_PROGRESS);

        taskManager.updateEpicStatus(newEpic.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, newEpic.getStatus(), "Некорректный статус у эпика");
    }
}