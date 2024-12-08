package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private static int maxId = 0;

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();

    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getSubtasksByEpic(Epic epic) {
        if (epic != null) {
            return epic.getSubtasks();
        }
        return null;
    }

    @Override
    public List<Subtask> getSubtasksByEpic(int epicId) {
        Epic epic = getEpicById(epicId);
        return getSubtasksByEpic(epic);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public void createTask(Task task) {
        task.setId(++maxId);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(++maxId);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        Epic epic = subtask.getEpic();
        List<Subtask> epicSubtasks = epic.getSubtasks();
        subtask.setId(++maxId);
        epicSubtasks.add(subtask);
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : getEpics()) {
            List<Subtask> epicSubtasks = epic.getSubtasks();
            epicSubtasks.clear();
        }
        subtasks.clear();
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        List<Subtask> epicSubtasks = epics.get(id).getSubtasks();
        for (Subtask s : epicSubtasks) {
            subtasks.remove(++maxId);
        }
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = subtask.getEpic();
        List<Subtask> epicSubtasks = epic.getSubtasks();

        epicSubtasks.remove(subtask);
        updateEpicStatus(subtask.getEpic().getId());
        subtasks.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateEpicStatus(subtask.getEpic().getId());
        }
    }

    @Override
    public void updateEpicStatus(int id) {
        Epic epic = epics.get(id);
        List<Subtask> subtasks = epic.getSubtasks();

        if (subtasks == null) {
            epic.setStatus(Status.NEW);
            return;
        }

        int newCount = 0;
        int doneCount = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            } else if (subtask.getStatus() == Status.NEW) newCount++;
            else doneCount++;
        }
        if (newCount == subtasks.size())
            epic.setStatus(Status.NEW);
        else if (doneCount == subtasks.size())
            epic.setStatus(Status.DONE);
        else
            epic.setStatus(Status.IN_PROGRESS);
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
