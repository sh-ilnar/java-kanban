package services;

import interfaces.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyViewTasks = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyViewTasks);
    }

    @Override
    public void add(Task task) {
        if (historyViewTasks.size() >= 10)
            historyViewTasks.removeFirst();

        // переделал, но если добавлять в историю саму таску, а не копию, то последний тест по ТЗ не выполнится
        historyViewTasks.add(task);
    }
}
