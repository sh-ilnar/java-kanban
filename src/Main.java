import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Epic;
import model.Task;
import services.Managers;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getFromBackedFile(historyManager, Path.of("file_backed.csv"));
        printAllTasks(taskManager);
        Task task1 = new Task("2 задача", "Описание 2");
        taskManager.createTask(task1);

        //Epic epic1 = new Epic("Первый эпик", "Описание эпика 1");
        //taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Второй эпик", "Описание эпика 2");
        taskManager.createEpic(epic2);

        printAllTasks(taskManager);

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task subtask : manager.getSubtasksByEpic(epic.getId())) {
                System.out.println("--> " + subtask);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        HistoryManager historyManager = manager.getHistoryManager();
        List<Task> history = historyManager.getHistory();
        for (Task task : history) {
            System.out.println(task);
        }
    }
}
