package services;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

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
        return epicsMap.get(epic.getId()).getSubtasks();
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
        Task task = new Task(name, description);
        task.setId(getNextId());
        tasksMap.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(String name, String description) {
        Epic epic = new Epic(name, description);
        epic.setId(getNextId());
        epicsMap.put(epic.getId(), epic);
        return epic;
    }

    public Subtask createSubtask(String name, String description, Epic epic) {
        Subtask subtask = new Subtask(name, description, epic);
        subtask.setId(getNextId());
        epic.getSubtasks().add(subtask);
        subtasksMap.put(subtask.getId(), subtask);
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
            subtasksMap.remove(s.getId());
        }
        epicsMap.remove(id);
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasksMap.get(id);
        Epic epic = subtask.getEpic();
        epic.getSubtasks().remove(subtask);
        updateEpicStatus(subtask.getEpic().getId());
        subtasksMap.remove(id);
    }

    public void updateTask(Task task) {
        if(tasksMap.containsKey(task.getId())) {
           tasksMap.put(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        if(epicsMap.containsKey(epic.getId())) {
            epicsMap.put(epic.getId(), epic);
        }
        updateEpicStatus(epic.getId());
    }

    public void updateSubtask(Subtask subtask) {
        if(subtasksMap.containsKey(subtask.getId())) {
            subtasksMap.put(subtask.getId(), subtask);
        }
        updateEpicStatus(subtask.getEpic().getId());
    }

    public void updateEpicStatus(int id) {
        Epic epic = epicsMap.get(id);
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
