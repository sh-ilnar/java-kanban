package services;

import interfaces.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> historyViewTasks = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return historyViewTasks;
    }

    public void add(Task task) {
        if (historyViewTasks.size() >= 10)
            historyViewTasks.removeFirst();

        Task viewedTask = Task.copyOf(task);
        historyViewTasks.add(viewedTask);
    }
}
