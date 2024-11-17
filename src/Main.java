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

        printAllTasks(taskManager);

        Epic epic1 = new Epic("Первый эпик", "Описание эпика 1");
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Второй эпик", "Описание эпика 2");
        taskManager.createEpic(epic2);

        Subtask subtask1_1 = new Subtask("Подзадача 1 эпика 1", "Описание", epic1);
        taskManager.createSubtask(subtask1_1);
        Subtask subtask1_2 = new Subtask("Подзадача 2 эпика 1", "Описание", epic1);
        taskManager.createSubtask(subtask1_2);
        Subtask subtask2_1 = new Subtask("Подзадача 1 эпика 2", "Описание", epic2);
        taskManager.createSubtask(subtask2_1);
        Subtask subtask2_2 = new Subtask("Подзадача 2 эпика 2", "Описание", epic2);
        taskManager.createSubtask(subtask2_2);

        Task viewTask1 = taskManager.getTaskById(1);
        Task viewTask2 = taskManager.getTaskById(2);
        Epic viewEpic1 = taskManager.getEpicById(3);
        Subtask viewSubtask1 = taskManager.getSubtaskById(5);
        Subtask viewSubtask2 = taskManager.getSubtaskById(6);
        Subtask viewSubtask3 = taskManager.getSubtaskById(7);
        Subtask viewSubtask4 = taskManager.getSubtaskById(8);
        Task viewTask3 = taskManager.getTaskById(1);
        Task viewTask4 = taskManager.getTaskById(2);
        Epic viewEpic2 = taskManager.getEpicById(3);
        Epic viewEpic3 = taskManager.getEpicById(3);
        Epic viewEpic4 = taskManager.getEpicById(3);

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
