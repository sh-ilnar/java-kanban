package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class FileBackedTaskManagerTest {

    @Test
    void save_addNewTask_savedNewTask() {
        try {
            Path tempFile = Files.createTempFile(null, null);
            HistoryManager historyManager = Managers.getDefaultHistory();
            TaskManager taskManager = Managers.getFromBackedFile(historyManager, tempFile);
            Task testTask = new Task("Тестовая задача", "Описание");
            taskManager.createTask(testTask);

            String fileContent = Files.readString(tempFile);

            Assertions.assertTrue(fileContent.contains("Тестовая задача"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromFile_loadEmptyFile_emptyTasks() {
        try {
            Path tempFile = Files.createTempFile(null, null);
            HistoryManager historyManager = Managers.getDefaultHistory();
            TaskManager taskManager = Managers.getFromBackedFile(historyManager, tempFile);

            List<Task> taskList = taskManager.getTasks();

            Assertions.assertEquals(0, taskList.size(), "Список задач не пустой");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromFile_loadFile_newTasks() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getFromBackedFile(historyManager, Path.of("test/resources/test_file_backed.csv"));

        Task task = taskManager.getTaskById(1);

        Assertions.assertEquals("Первая задача", task.getName(), "Ошибка загрузки файла");
    }

    @Test
    void loadFromFile_loadFile_countTasks() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getFromBackedFile(historyManager, Path.of("test/resources/test_file_backed.csv"));

        List<Task> taskList = taskManager.getTasks();

        Assertions.assertEquals(3, taskList.size(), "Ошибка загрузки файла");
    }
}