package services;

import interfaces.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private LinkedTaskList historyViewTasks = new LinkedTaskList();

    @Override
    public List<Task> getHistory() {
        return historyViewTasks.getTasks();
    }

    @Override
    public void add(Task task) {
        historyViewTasks.linkLast(task);
    }

    @Override
    public void remove(int id) {
        historyViewTasks.removeNode(id);
    }
}
