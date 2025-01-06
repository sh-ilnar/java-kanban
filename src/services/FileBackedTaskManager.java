package services;

import interfaces.HistoryManager;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpicStatus(int id) {
        super.updateEpicStatus(id);
        save();
    }

    void save() {

        List<String> rows = new ArrayList<>();

        rows.add("id, type, name, status, description, epic" + System.lineSeparator());

        for (Task t : this.getTasks()) {
            String row = t.toCsvRow();
            rows.add(row);
        }

        for (Epic e : this.getEpics()) {
            String row = e.toCsvRow();
            rows.add(row);
        }

        for (Subtask s : this.getSubtasks()) {
            String row = s.toCsvRow();
            rows.add(row);
        }

        try (FileWriter fileWriter = new FileWriter(this.file, StandardCharsets.UTF_8)) {
            for (String e : rows) {
                fileWriter.write(e);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void taskFromString(String value) {
        String[] cols = value.split(",");
        Type type = Type.valueOf(cols[1]);

        switch (type) {
            case Type.TASK:
                Task task = new Task(Integer.parseInt(cols[0]), cols[2], cols[4], Status.valueOf(cols[3]));
                tasks.put(task.getId(), task);
                if (task.getId() > maxId) maxId = task.getId();
                break;
            case Type.EPIC:
                Epic epic = new Epic(Integer.parseInt(cols[0]), cols[2], cols[4], Status.valueOf(cols[3]));
                epics.put(epic.getId(), epic);
                if (epic.getId() > maxId) maxId = epic.getId();
                break;
            case Type.SUBTASK:
                if (epics.containsKey(Integer.parseInt(cols[5]))) {
                    Epic subtaskEpic = epics.get(Integer.parseInt(cols[5]));
                    Subtask subtask = new Subtask(Integer.parseInt(cols[0]), cols[2], cols[4], Status.valueOf(cols[3]), subtaskEpic);
                    subtasks.put(subtask.getId(), subtask);
                    if (subtask.getId() > maxId) maxId = subtask.getId();
                }
                break;
            default:
                break;
        }
    }

    static FileBackedTaskManager loadFromFile(HistoryManager historyManager, File file) {

        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager, file);

        List<String> rows = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.getPath(), StandardCharsets.UTF_8))) {
            while (fileReader.ready()) {
                String line = fileReader.readLine();
                if (!line.startsWith("id, type, name, status, description, epic")) {
                    rows.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        if (!rows.isEmpty()) {
            for (String row : rows) {
                taskManager.taskFromString(row);
            }
        }

        return taskManager;
    }
}
