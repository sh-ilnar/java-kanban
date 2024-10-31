import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import services.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Первая задача", "Описание 1");
        taskManager.createTask(task1);
        Task task2 = new Task("Вторая задача", "Описание 2");
        taskManager.createTask(task2);

        System.out.println(taskManager.getTasks());

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

        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

        Task test_task = new Task("Первая задача (обновлено)", "Описание");
        test_task.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(test_task);

        System.out.println(taskManager.getTasks());

        taskManager.deleteTask(2);

        System.out.println(taskManager.getTasks());

        taskManager.deleteEpic(3);

        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

        taskManager.deleteAllSubtasks();
        System.out.println(taskManager.getSubtasks());

        Subtask subtask3 = new Subtask("Подзадача 1 эпика 1", "Описание", epic1);
        taskManager.createSubtask(subtask3);
        System.out.println(taskManager.getSubtasks());
    }
}
