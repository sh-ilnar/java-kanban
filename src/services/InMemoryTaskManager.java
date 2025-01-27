package services;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private static int maxId = 0;

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private Set<Task> prioritizedTasks = new TreeSet<>();

    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int id) {
        maxId = id;
    }

    public Map<Integer, Task> getTasksMap() {
        return tasks;
    }

    public Map<Integer, Epic> getEpicsMap() {
        return epics;
    }

    public Map<Integer, Subtask> getSubpaskMap() {
        return subtasks;
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
        if (isDateIntersection(task)) {
            throw new IllegalArgumentException("Задача " + task.getName() + " пересекается с другими задачами.");
        }
        else {
            task.setId(++maxId);
            tasks.put(task.getId(), task);
            if (!task.getStartTime().equals(Task.minDate)) {
                prioritizedTasks.add(task);
            }
        }
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(++maxId);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (isDateIntersection(subtask)) {
            throw new IllegalArgumentException("Подзадача " + subtask.getName() + " пересекается с другими задачами.");
        }
        else {
            Epic epic = subtask.getEpic();
            List<Subtask> epicSubtasks = epic.getSubtasks();
            subtask.setId(++maxId);
            epicSubtasks.add(subtask);
            updateEpicDuration(epic.getId());
            updateEpicStartEndTime(subtask.getEpic().getId());
            subtasks.put(subtask.getId(), subtask);

            if (!subtask.getStartTime().equals(Task.minDate)) {
                prioritizedTasks.add(subtask);
            }
        }
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
        getEpics().stream()
                .map(Epic::getSubtasks)
                .forEach(List::clear);
        subtasks.clear();
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        List<Subtask> epicSubtasks = epics.get(id).getSubtasks();
        epicSubtasks.forEach(s -> subtasks.remove(++maxId));
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = subtask.getEpic();
        List<Subtask> epicSubtasks = epic.getSubtasks();

        epicSubtasks.remove(subtask);
        updateEpicStatus(subtask.getEpic().getId());
        updateEpicDuration(subtask.getEpic().getId());
        updateEpicStartEndTime(subtask.getEpic().getId());
        subtasks.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            if (isDateIntersection(task)) {
                throw new IllegalArgumentException("Задача " + task.getName() + " пересекается с другими задачами.");
            }
            else {
                tasks.put(task.getId(), task);
            }
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
            if (isDateIntersection(subtask)) {
                throw new IllegalArgumentException("Подзадача " + subtask.getName() + " пересекается с другими задачами.");
            }
            else {
                subtasks.put(subtask.getId(), subtask);
                updateEpicStatus(subtask.getEpic().getId());
                updateEpicDuration(subtask.getEpic().getId());
                updateEpicStartEndTime(subtask.getEpic().getId());
            }
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
    public void updateEpicDuration(int id) {
        Epic epic = epics.get(id);
        List<Subtask> subtasks = epic.getSubtasks();

        if (subtasks == null) {
            epic.setDuration(Duration.ZERO);
            return;
        }

        Duration newDuration = Duration.ZERO;
        for (Subtask subtask : subtasks) {
            newDuration = newDuration.plus(subtask.getDuration());
        }
        epic.setDuration(newDuration);
    }

    @Override
    public void updateEpicStartEndTime(int id) {
        Epic epic = epics.get(id);
        List<Subtask> subtasks = epic.getSubtasks();

        if (subtasks == null) {
            return;
        }

        subtasks.forEach(subtask -> {
            if (epic.getStartTime().equals(Task.minDate) || epic.getStartTime().isAfter(subtask.getStartTime())) {
                epic.setStartTime(subtask.getStartTime());
            }
            if (epic.getEndTime().isBefore(subtask.getEndTime())) {
                epic.setEndTime(subtask.getEndTime());
            }
        });
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public boolean isDateIntersection(Task task) {
        return prioritizedTasks.stream()
                .filter(x -> !x.getStartTime().equals(Task.minDate))
                .anyMatch(x -> (x.getEndTime().isAfter(task.getStartTime()) && x.getStartTime().isBefore(task.getStartTime()))
                            || (x.getStartTime().isBefore(task.getEndTime()) && x.getEndTime().isAfter(task.getStartTime())));
    }
}
