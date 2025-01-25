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
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getDefault(historyManager);
        Task task = new Task("Тест", "Тестовое описание");
        taskManager.createTask(task);
        Task gettingTask = taskManager.getTaskById(task.getId());

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не найдена");
        assertEquals(1, history.size(), "В истории не 1 элемент");
    }

    @Test
    void add_addTask_notEmptyHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getDefault(historyManager);
        Task task = new Task("Тест", "Тестовое описание");
        taskManager.createTask(task);

        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не найдена");
        assertEquals(1, history.size(), "В истории не 1 элемент");
    }
}