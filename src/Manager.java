import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private HashMap<Integer, Task> tasksMap;
    private HashMap<Integer, Epic> epicsMap;
    private HashMap<Integer, Subtask> subtasksMap;

    private static int maxId;

    public Manager() {
        this.tasksMap = new HashMap<>();
        this.epicsMap = new HashMap<>();
        this.subtasksMap = new HashMap<>();
        maxId = 0;
    }

    public static int getNextId() {
        maxId++;
        return maxId;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasksMap;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epicsMap;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasksMap;
    }

    public ArrayList<Subtask> getSubtasksByEpic(Epic epic) {
        return epicsMap.get(epic.id).getSubtasks();
    }

    public Task getTask(int id) {
        return tasksMap.get(id);
    }

    public Epic getEpic(int id) {
        return epicsMap.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasksMap.get(id);
    }

    public Task createTask(String name, String description) {
        Task task = new Task(getNextId(), name, description);
        tasksMap.put(task.id, task);
        return task;
    }

    public Epic createEpic(String name, String description) {
        Epic epic = new Epic(getNextId(), name, description);
        epicsMap.put(epic.id, epic);
        return epic;
    }

    public Subtask createSubtask(String name, String description, Epic epic) {
        Subtask subtask = new Subtask(getNextId(), name, description, epic);
        epic.addSubtask(subtask);
        subtasksMap.put(subtask.id, subtask);
        return subtask;
    }

    public void deleteAllTasks() {
        tasksMap.clear();
    }

    public void deleteAllEpics() {
        subtasksMap.clear();
        epicsMap.clear();
    }

    public void deleteAllSubtasks() {
        subtasksMap.clear();
    }

    public void deleteTask(int id) {
        tasksMap.remove(id);
    }

    public void deleteEpic(int id) {
        ArrayList<Subtask> epicSubtasks = epicsMap.get(id).getSubtasks();
        for (Subtask s: epicSubtasks) {
            subtasksMap.remove(s.id);
        }
        epicsMap.remove(id);
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasksMap.get(id);
        Epic epic = subtask.getEpic();
        epic.deleteSubtask(subtask);
        epic.updateStatus();
        subtasksMap.remove(id);
    }

    public void updateTask(Task task) {
        tasksMap.get(task.id).update(task);
    }

    public void updateEpic(Epic epic) {
        epicsMap.get(epic.id).update(epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasksMap.get(subtask.id).update(subtask);
    }
}
