package services;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private static int maxId = 0;

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getSubtasksByEpic(Epic epic) {
        if (epic != null) {
            return epic.getSubtasks();
        }
        return null;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public void createTask(Task task) {
        task.setId(++maxId);
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(++maxId);
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        Epic epic = subtask.getEpic();
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();
        subtask.setId(++maxId);
        epicSubtasks.add(subtask);
        subtasks.put(subtask.getId(), subtask);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    public void deleteAllSubtasks() {
        for(Epic epic : getEpics()) {
            ArrayList<Subtask> epicSubtasks = epic.getSubtasks();
            epicSubtasks.clear();
        }
        subtasks.clear();
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        ArrayList<Subtask> epicSubtasks = epics.get(id).getSubtasks();
        for (Subtask s: epicSubtasks) {
            subtasks.remove(++maxId);
        }
        epics.remove(id);
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = subtask.getEpic();
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();

        epicSubtasks.remove(subtask);
        updateEpicStatus(subtask.getEpic().getId());
        subtasks.remove(id);
    }

    public void updateTask(Task task) {
        if(tasks.containsKey(task.getId())) {
           tasks.put(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        if(epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic.getId());
        }
    }

    public void updateSubtask(Subtask subtask) {
        if(subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateEpicStatus(subtask.getEpic().getId());
        }
    }

    public void updateEpicStatus(int id) {
        Epic epic = epics.get(id);
        ArrayList<Subtask> subtasks = epic.getSubtasks();

        if (subtasks == null) {
            epic.setStatus(Status.NEW);
            return;
        }

        int newCount = 0;
        int doneCount = 0;
        for (Subtask subtask : subtasks) {
            if(subtask.getStatus() == Status.IN_PROGRESS) {
                subtask.setStatus(Status.IN_PROGRESS);
                return;
            }
            else if (subtask.getStatus() == Status.NEW) newCount++;
            else doneCount++;
        }
        if (newCount == subtasks.size())
            epic.setStatus(Status.NEW);
        else if (doneCount == subtasks.size())
            epic.setStatus(Status.DONE);
    }
}
