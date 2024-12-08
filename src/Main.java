import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import services.Managers;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getDefault(historyManager);

        Task task1 = new Task("Первая задача", "Описание 1");
        taskManager.createTask(task1);
        Task task2 = new Task("Вторая задача", "Описание 2");
        taskManager.createTask(task2);
        Task task3 = new Task("Третья задача", "Описание 3");
        taskManager.createTask(task3);
        Task task4 = new Task("Четвертая задача", "Описание 4");
        taskManager.createTask(task4);
        Task task5 = new Task("Пятая задача", "Описание 5");
        taskManager.createTask(task5);
        Task task6 = new Task("Шестая задача", "Описание 6");
        taskManager.createTask(task6);
        Task task7 = new Task("Седьмая задача", "Описание 7");
        taskManager.createTask(task7);
        Task task8 = new Task("Восьмая задача", "Описание 8");
        taskManager.createTask(task8);
        Task task9 = new Task("Девятая задача", "Описание 9");
        taskManager.createTask(task9);
        Task task10 = new Task("Десятая задача", "Описание 10");
        taskManager.createTask(task10);

        printAllTasks(taskManager);
        System.out.println("=======");

        Epic epic1 = new Epic("Первый эпик", "Описание эпика 1");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Второй эпик", "Описание эпика 2");
        taskManager.createEpic(epic2);

        Task viewTask1 = taskManager.getTaskById(1);
        Task viewTask2 = taskManager.getTaskById(2);
        Epic viewEpic1 = taskManager.getEpicById(11);
        Task viewTask3 = taskManager.getTaskById(1);
        Task viewTask4 = taskManager.getTaskById(2);
        Epic viewEpic2 = taskManager.getEpicById(12);
        Task viewTask5 = taskManager.getTaskById(4);
        Task viewTask6 = taskManager.getTaskById(5);
        Task viewTask7 = taskManager.getTaskById(1);
        Task viewTask8 = taskManager.getTaskById(10);
        Task viewTask9 = taskManager.getTaskById(9);
        Task viewTask10 = taskManager.getTaskById(8);

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
