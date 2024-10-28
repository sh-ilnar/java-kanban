import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import services.Manager;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        Task task1 = manager.createTask("Первая задача", "Описание 1");
        Task task2 = manager.createTask("Вторая задача", "Описание 2");

        System.out.println(manager.getTasks());

        Epic epic1 = manager.createEpic("Первый эпик", "Описание эпика 1");
        Epic epic2 = manager.createEpic("Второй эпик", "Описание эпика 2");

        Subtask subtask1_1 = manager.createSubtask("Подзадача 1 эпика 1", "Описание", epic1);
        Subtask subtask1_2 = manager.createSubtask("Подзадача 2 эпика 1", "Описание", epic1);
        Subtask subtask2_1 = manager.createSubtask("Подзадача 1 эпика 2", "Описание", epic2);
        Subtask subtask2_2 = manager.createSubtask("Подзадача 2 эпика 2", "Описание", epic2);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        Task test_task = new Task("Первая задача (обновлено)", "Описание");
        test_task.setStatus(Status.IN_PROGRESS);
        manager.updateTask(test_task);

        System.out.println(manager.getTasks());

        manager.deleteTask(2);

        System.out.println(manager.getTasks());

        manager.deleteEpic(3);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
    }
}
