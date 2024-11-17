package interfaces;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {

    public List<Task> getTasks();

    public List<Epic> getEpics();

    public List<Subtask> getSubtasks();

    public List<Subtask> getSubtasksByEpic(Epic epic);

    public List<Subtask> getSubtasksByEpic(int epicId);

    public Task getTaskById(int id);

    public Epic getEpicById(int id);

    public Subtask getSubtaskById(int id);

    public void createTask(Task task);

    public void createEpic(Epic epic);

    public void createSubtask(Subtask subtask);

    public void deleteAllTasks();

    public void deleteAllEpics();

    public void deleteAllSubtasks();

    public void deleteTaskById(int id);

    public void deleteEpicById(int id);

    public void deleteSubtaskById(int id);

    public void updateTask(Task task);

    public void updateEpic(Epic epic);

    public void updateSubtask(Subtask subtask);

    public void updateEpicStatus(int id);

    public HistoryManager getHistoryManager();
}
