package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    Path tempFile = Path.of("test/resources/test_file_backed.csv");

    @Override
    TaskManager createTaskManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        return Managers.getFromBackedFile(historyManager, tempFile);
    }

    @Test
    void save_addNewTask_savedNewTask() {
        try {
            Task testTask = new Task("Тестовая задача", "Описание", Duration.of(100, ChronoUnit.MINUTES), LocalDateTime.parse("10.02.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
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
        TaskManager newTaskManager = Managers.getFromBackedFile(historyManager, Path.of("test/resources/test_file_for_load.csv"));

        Task task = taskManager.getTaskById(1);

        Assertions.assertEquals("Тестовая задача", task.getName(), "Ошибка загрузки файла");
    }
}