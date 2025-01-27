import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import services.Managers;

//import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getDefault(historyManager);
//        TaskManager taskManager = Managers.getFromBackedFile(historyManager, Path.of("file_backed.csv"));
//        printAllTasks(taskManager);

        System.out.println("-------");

        Task task1 = new Task("Задача 1", "Описание новой задачи", Duration.of(100, ChronoUnit.MINUTES), LocalDateTime.parse("20.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        try {
            taskManager.createTask(task1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Task task2 = new Task("Задача 2", "Описание новой задачи", Duration.of(20, ChronoUnit.MINUTES), LocalDateTime.parse("20.01.2025 01:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        try {
            taskManager.createTask(task2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Task task3 = new Task("Задача 3", "Описание новой задачи", Duration.of(30, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 03:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        try {
            taskManager.createTask(task3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Task task4 = new Task("Задача 4", "Описание новой задачи", Duration.of(100, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 02:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        try {
            taskManager.createTask(task4);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Task task5 = new Task("Задача 5", "Описание новой задачи", Duration.of(100, ChronoUnit.MINUTES), LocalDateTime.parse("01.01.2025 01:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        try {
            taskManager.createTask(task5);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Task task6 = new Task("Задача 6", "Описание новой задачи", Duration.of(100, ChronoUnit.MINUTES));
        try {
            taskManager.createTask(task6);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Task task7 = new Task("Задача 7", "Описание новой задачи", Duration.of(100, ChronoUnit.MINUTES));
        try {
            taskManager.createTask(task7);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Epic epic1 = new Epic("1 эпик", "Описание эпика 1");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("2 эпик", "Описание эпика 2");
        taskManager.createEpic(epic2);

        Subtask subtask1_1 = new Subtask("Подзадача 1 эпика 1", "Описание", Duration.of(100, ChronoUnit.MINUTES), LocalDateTime.parse("02.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), epic1);
        try {
            taskManager.createSubtask(subtask1_1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Subtask subtask1_2 = new Subtask("Подзадача 2 эпика 1", "Описание", Duration.of(10, ChronoUnit.MINUTES), LocalDateTime.parse("02.01.2025 01:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), epic1);
        try {
            taskManager.createSubtask(subtask1_2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Subtask subtask2_1 = new Subtask("Подзадача 1 эпика 2", "Описание", Duration.of(15, ChronoUnit.MINUTES), LocalDateTime.parse("03.01.2025 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), epic2);
        try {
            taskManager.createSubtask(subtask2_1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        Subtask subtask2_2 = new Subtask("Подзадача 2 эпика 2", "Описание", Duration.of(1, ChronoUnit.MINUTES), LocalDateTime.now(), epic2);
        try {
            taskManager.createSubtask(subtask2_2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        printAllTasks(taskManager);

        System.out.println("-------");
        System.out.println("Приоритизированные задачи:");

        Set<Task> priorityTasks = taskManager.getPrioritizedTasks();
        for (Task task : priorityTasks) {
            System.out.println(task);
        }
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
