import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import services.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("Первая задача", "Описание 1");
        Task task2 = taskManager.createTask("Вторая задача", "Описание 2");

        System.out.println(taskManager.getTasks());

        Epic epic1 = taskManager.createEpic("Первый эпик", "Описание эпика 1");
        Epic epic2 = taskManager.createEpic("Второй эпик", "Описание эпика 2");

        Subtask subtask1_1 = taskManager.createSubtask("Подзадача 1 эпика 1", "Описание", epic1);
        Subtask subtask1_2 = taskManager.createSubtask("Подзадача 2 эпика 1", "Описание", epic1);
        Subtask subtask2_1 = taskManager.createSubtask("Подзадача 1 эпика 2", "Описание", epic2);
        Subtask subtask2_2 = taskManager.createSubtask("Подзадача 2 эпика 2", "Описание", epic2);

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

        Subtask subtask3 = taskManager.createSubtask("Подзадача 1 эпика 1", "Описание", epic1);
        System.out.println(taskManager.getSubtasks());
    }
}
