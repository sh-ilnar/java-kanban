package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {

    @Test
    void getHistory_getTask_returnedHistory() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Тест", "Тестовое описание");
        taskManager.createTask(task);
        HistoryManager historyManager = taskManager.getHistoryManager();
        Task gettingTask = taskManager.getTaskById(task.getId());

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не найдена");
        assertEquals(1, history.size(), "В истории не 1 элемент");
    }

    @Test
    void add_addTask_notEmptyHistory() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Тест", "Тестовое описание");
        taskManager.createTask(task);
        HistoryManager historyManager = taskManager.getHistoryManager();

        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не найдена");
        assertEquals(1, history.size(), "В истории не 1 элемент");
    }

    @Test
    void add_updateTask_notUpdatedHistory() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Первоначальное наименование", "Тестовое описание");
        taskManager.createTask(task);
        HistoryManager historyManager = taskManager.getHistoryManager();
        historyManager.add(task);

        task.setName("Измененное наименование");
        historyManager.add(task);

        final List<Task> history = historyManager.getHistory();
        Task viewedTask1 = history.get(0);
        Task viewedTask2 = history.get(1);

        assertEquals("Первоначальное наименование", viewedTask1.getName(), "Версия была изменена");
        assertEquals("Измененное наименование", viewedTask2.getName(), "Версия была изменена");
    }
}